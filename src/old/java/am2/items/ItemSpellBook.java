package am2.items;

import java.util.List;
import java.util.Map;

import am2.ArsMagica2;
import am2.container.InventorySpellBook;
import am2.defs.IDDefs;
import am2.defs.ItemDefs;
import am2.enchantments.AMEnchantmentHelper;
import am2.enchantments.AMEnchantments;
import am2.extensions.SkillData;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SuppressWarnings("deprecation")
public class ItemSpellBook extends ItemArsMagica{

	public static final byte ID_NEXT_SPELL = 0;
	public static final byte ID_PREV_SPELL = 1;
	
//	private final String[] npc_textureFiles = {"affinity_tome_general", "affinity_tome_ice", "affinity_tome_life", "affinity_tome_fire", "affinity_tome_lightning", "affinity_tome_ender"};
//	private final String[] player_textureFiles = {"spell_book_cover", "spell_book_decoration"};

	public ItemSpellBook(){
		super();
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack){
		ItemStack activeSpell = GetActiveItemStack(par1ItemStack);
		if (activeSpell != null){
			return String.format("\2477%s (" + activeSpell.getDisplayName() + "\2477)", I18n.translateToLocal("item.arsmagica2:spellbook.name"));
		}
		return I18n.translateToLocal("item.arsmagica2:spellbook.name");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack){
		if (getMaxItemUseDuration(itemstack) == 0){
			return EnumAction.NONE;
		}
		return EnumAction.BLOCK;
	}

	@Override
	public final int getMaxItemUseDuration(ItemStack itemstack){
		ItemSpellBase scroll = GetActiveScroll(itemstack);
		if (scroll != null){
			return scroll.getMaxItemUseDuration(itemstack);
		}
		return 0;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.isSneaking()){
			FMLNetworkHandler.openGui(playerIn, ArsMagica2.instance, IDDefs.GUI_SPELL_BOOK, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}

		playerIn.setActiveHand(hand);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	private ItemStack[] getMyInventory(ItemStack itemStack){
		return ReadFromStackTagCompound(itemStack);
	}

	public ItemStack[] getActiveScrollInventory(ItemStack bookStack){
		ItemStack[] inventoryItems = getMyInventory(bookStack);
		ItemStack[] returnArray = new ItemStack[8];
		for (int i = 0; i < 8; ++i){
			returnArray[i] = inventoryItems[i];
		}
		return returnArray;
	}

	public ItemSpellBase GetActiveScroll(ItemStack bookStack){
		ItemStack[] inventoryItems = getMyInventory(bookStack);
		if (inventoryItems[GetActiveSlot(bookStack)] == null){
			return null;
		}
		return (ItemSpellBase)inventoryItems[GetActiveSlot(bookStack)].getItem();
	}

	public ItemStack GetActiveItemStack(ItemStack bookStack){
		ItemStack[] inventoryItems = getMyInventory(bookStack);
		if (inventoryItems[GetActiveSlot(bookStack)] == null){
			return null;
		}
		return inventoryItems[GetActiveSlot(bookStack)].copy();
	}

	public void replaceAciveItemStack(ItemStack bookStack, ItemStack newstack){
		ItemStack[] inventoryItems = getMyInventory(bookStack);
		int index = GetActiveSlot(bookStack);
		inventoryItems[index] = newstack;
		UpdateStackTagCompound(bookStack, inventoryItems);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving.isSneaking()){
			FMLNetworkHandler.openGui((EntityPlayer) entityLiving, ArsMagica2.instance, IDDefs.GUI_SPELL_BOOK, worldIn, (int)entityLiving.posX, (int)entityLiving.posY, (int)entityLiving.posZ);
		}else{
			ItemStack currentSpellStack = GetActiveItemStack(stack);
			if (currentSpellStack != null){
				ItemDefs.spell.onPlayerStoppedUsing(currentSpellStack, worldIn, entityLiving, timeLeft);
			}
		}
	}

	public void UpdateStackTagCompound(ItemStack itemStack, ItemStack[] values){
		if (itemStack.getTagCompound() == null){
			itemStack.setTagCompound(new NBTTagCompound());
		}

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < values.length; ++i){
			ItemStack stack = values[i];
			NBTTagCompound spell = new NBTTagCompound();
			if (stack != null){
				spell.setInteger("meta", stack.getItemDamage());
				spell.setInteger("index", i);
				if (stack.getTagCompound() != null){
					spell.setTag("data", stack.getTagCompound());
				}
				list.appendTag(spell);
			}
		}

		itemStack.getTagCompound().setTag("spell_book_inventory", list);

		ItemStack active = GetActiveItemStack(itemStack);
		boolean Soulbound = EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, itemStack) > 0;
		if (active != null)
			AMEnchantmentHelper.copyEnchantments(active, itemStack);
		if (Soulbound)
			AMEnchantmentHelper.soulbindStack(itemStack);
	}

	public void SetActiveSlot(ItemStack itemStack, int slot){
		if (itemStack.getTagCompound() == null){
			itemStack.setTagCompound(new NBTTagCompound());
		}
		if (slot < 0) slot = 0;
		if (slot > 7) slot = 7;
		itemStack.getTagCompound().setInteger("spellbookactiveslot", slot);

		ItemStack active = GetActiveItemStack(itemStack);
		boolean Soulbound = EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, itemStack) > 0;
		if (active != null)
			AMEnchantmentHelper.copyEnchantments(active, itemStack);
		if (Soulbound)
			AMEnchantmentHelper.soulbindStack(itemStack);
	}

	public int SetNextSlot(ItemStack itemStack){
		int slot = GetActiveSlot(itemStack);
		int newSlot = slot;

		do{
			newSlot++;
			if (newSlot > 7) newSlot = 0;
			SetActiveSlot(itemStack, newSlot);
		}while (GetActiveScroll(itemStack) == null && newSlot != slot);
		return slot;
	}

	public int SetPrevSlot(ItemStack itemStack){
		int slot = GetActiveSlot(itemStack);
		int newSlot = slot;

		do{
			newSlot--;
			if (newSlot < 0) newSlot = 7;
			SetActiveSlot(itemStack, newSlot);
		}while (GetActiveScroll(itemStack) == null && newSlot != slot);
		return slot;
	}

	public int GetActiveSlot(ItemStack itemStack){
		if (itemStack.getTagCompound() == null){
			SetActiveSlot(itemStack, 0);
			return 0;
		}
		return itemStack.getTagCompound().getInteger("spellbookactiveslot");
	}

	public ItemStack[] ReadFromStackTagCompound(ItemStack itemStack){
		if (itemStack.getTagCompound() == null){
			return new ItemStack[InventorySpellBook.inventorySize];
		}
		ItemStack[] items = new ItemStack[InventorySpellBook.inventorySize];
		/*for (int i = 0; i < items.length; ++i){
			if (!itemStack.stackTagCompound.hasKey("spellbookitem" + i) || itemStack.stackTagCompound.getInteger("spellbookitem" + i) == -1){
				items[i] = null;
				continue;
			}
			int id = itemStack.stackTagCompound.getInteger("spellbookitem" + i);
			int meta = 0;
			NBTTagCompound compound = null;

			if (itemStack.stackTagCompound.hasKey("spellbookmeta" + i))
				meta = itemStack.stackTagCompound.getInteger("spellbookmeta" + i);
			if (itemStack.stackTagCompound.hasKey("spellbooktag" + i))
				compound = itemStack.stackTagCompound.getCompoundTag("spellbooktag" + i);
			items[i] = new ItemStack(Item.itemsList[id], 1, meta);
			if (compound != null){
				items[i].stackTagCompound = compound;
			}
		}*/

		NBTTagList list = itemStack.getTagCompound().getTagList("spell_book_inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i){
			NBTTagCompound spell = (NBTTagCompound)list.getCompoundTagAt(i);
			int meta = spell.getInteger("meta");
			NBTTagCompound tag = spell.getCompoundTag("data");
			int index = spell.getInteger("index");
			items[index] = new ItemStack(ItemDefs.spell, 1, meta);
			items[index].setTagCompound(tag);

		}
		return items;
	}

	public InventorySpellBook ConvertToInventory(ItemStack bookStack){
		InventorySpellBook isb = new InventorySpellBook();
		isb.SetInventoryContents(getMyInventory(bookStack));
		return isb;
	}

	@Override
	public boolean getShareTag(){
		return true;
	}

	public String GetActiveSpellName(ItemStack bookStack){
		ItemStack stack = GetActiveItemStack(bookStack);
		if (stack == null){
			return I18n.translateToLocal("am2.tooltip.none");
		}
		return stack.getDisplayName();
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4){
		ItemSpellBase activeScroll = GetActiveScroll(par1ItemStack);
		ItemStack stack = GetActiveItemStack(par1ItemStack);

		String s = I18n.translateToLocal("am2.tooltip.open");
		String s2 = I18n.translateToLocal("am2.tooltip.scroll");
		par3List.add((new StringBuilder()).append("\2477").append(s).toString());
		par3List.add((new StringBuilder()).append("\2477").append(s2).toString());
		if (activeScroll != null){
			activeScroll.addInformation(stack, par2EntityPlayer, par3List, par4);
		}

		par3List.add("\247c" + I18n.translateToLocal("am2.tooltip.spellbookWarning1") + "\247f");
		par3List.add("\247c" + I18n.translateToLocal("am2.tooltip.spellbookWarning2") + "\247f");
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
		ItemStack scrollStack = GetActiveItemStack(stack);
		if (scrollStack != null){
			ItemDefs.spell.onUsingTick(scrollStack, player, count);
		}
	}

	@Override
	public boolean isBookEnchantable(ItemStack bookStack, ItemStack enchantBook){
		Map<Enchantment, Integer> enchantMap = EnchantmentHelper.getEnchantments(enchantBook);
		for (Enchantment o : enchantMap.keySet()){
			if (o == AMEnchantments.soulbound){
				return true;
			}
		}
		return false;
	}

	@Override
	public int getItemEnchantability(){
		return 1;
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack){
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
		super.onUpdate(stack, world, entity, par4, par5);
		if (entity instanceof EntityPlayerSP){
			EntityPlayerSP player = (EntityPlayerSP)entity;
			ItemStack usingItem = player.getActiveItemStack();
			if (usingItem != null && usingItem.getItem() == this){
				if (SkillData.For(player).hasSkill("spell_motion")){
					player.movementInput.moveForward *= 2.5F;
					player.movementInput.moveStrafe *= 2.5F;
				}
			}
		}
	}
}









