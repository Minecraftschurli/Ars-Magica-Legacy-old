package am2.spell.shape;

import java.util.EnumSet;

import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.ItemDefs;
import am2.items.ItemBindingCatalyst;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.spell.SpellCastResult;
import am2.utils.InventoryUtilities;
import am2.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Binding extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		if (!(caster instanceof EntityPlayer)){
			return SpellCastResult.EFFECT_FAILED;
		}
		
		EntityPlayer player = (EntityPlayer)caster;
		ItemStack heldStack = player.getActiveItemStack();
		if (heldStack == null || heldStack.getItem() != ItemDefs.spell){
			return SpellCastResult.EFFECT_FAILED;
		}
		int bindingType = getBindingType(SpellUtils.merge(heldStack.copy()));
		switch (bindingType){
		case ItemBindingCatalyst.META_AXE:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundAxe);
			break;
		case ItemBindingCatalyst.META_PICK:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundPickaxe);
			break;
		case ItemBindingCatalyst.META_SWORD:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundSword);
			break;
		case ItemBindingCatalyst.META_SHOVEL:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundShovel);
			break;
		case ItemBindingCatalyst.META_HOE:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundHoe);
			break;
		case ItemBindingCatalyst.META_BOW:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundBow);
			break;
		case ItemBindingCatalyst.META_SHIELD:
			heldStack = InventoryUtilities.replaceItem(heldStack, ItemDefs.BoundShield);
			break;
		}
		player.inventory.setInventorySlotContents(player.inventory.currentItem, heldStack);
		return SpellCastResult.SUCCESS;
	}

	@Override
	public boolean isChanneled(){
		return false;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_CHIMERITE),
				Items.WOODEN_SWORD,
				Items.STONE_SHOVEL,
				Items.IRON_HOE,
				Items.GOLDEN_AXE,
				Items.DIAMOND_PICKAXE,
				new ItemStack(ItemDefs.bindingCatalyst, 1, OreDictionary.WILDCARD_VALUE)
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1;
	}

	@Override
	public boolean isTerminusShape(){
		return false;
	}

	@Override
	public boolean isPrincipumShape(){
		return true;
	}

//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		return "arsmagica2:spell.binding.cast";
//	}

//	public void setBindingType(ItemStack craftStack, ItemStack addedBindingCatalyst){
//		SpellUtils.instance.setSpellMetadata(craftStack, "binding_type", "" + addedBindingCatalyst.getItemDamage());
//	}

	public int getBindingType(ItemStack spellStack){
		int type = 0;
		try{
			type = Integer.parseInt(SpellUtils.getSpellMetadata(spellStack, "binding_type"));
		}catch (Throwable t){

		}
		return type;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {
		for (Object obj : recipe) {
			if (obj instanceof ItemStack) {
				ItemStack is = (ItemStack)obj;
				if (is.getItem().equals(ItemDefs.bindingCatalyst))
					tag.setString("binding_type", "" + is.getItemDamage());
			}
		}
	}
}
