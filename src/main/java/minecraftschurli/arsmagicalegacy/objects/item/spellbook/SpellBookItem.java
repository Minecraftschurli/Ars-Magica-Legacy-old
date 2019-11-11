package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.Items;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookItem extends Item {
    public SpellBookItem() {
        super(new Properties().maxStackSize(1).rarity(Rarity.EPIC).group(ArsMagicaLegacy.ITEM_GROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (!world.isRemote && player instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {

                    @Override
                    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                        return new SpellBookContainer(id, playerInventory, new SpellBookInventory());
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return stack.getDisplayName();
                    }
                });
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack){
        if (getUseDuration(itemstack) == 0){
            return UseAction.NONE;
        }
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        SpellItem scroll = getActiveScroll(stack);
        if (scroll != null){
            return scroll.getUseDuration(stack);
        }
        return 0;
    }

    private ItemStack[] getMyInventory(ItemStack itemStack){
        return readFromStackTagCompound(itemStack);
    }

    public ItemStack[] getActiveScrollInventory(ItemStack bookStack){
        ItemStack[] inventoryItems = getMyInventory(bookStack);
        ItemStack[] returnArray = new ItemStack[8];
        for (int i = 0; i < 8; ++i){
            returnArray[i] = inventoryItems[i];
        }
        return returnArray;
    }

    public SpellItem getActiveScroll(ItemStack bookStack){
        ItemStack[] inventoryItems = getMyInventory(bookStack);
        if (inventoryItems[getActiveSlot(bookStack)].isEmpty()){
            return null;
        }
        return (SpellItem)inventoryItems[getActiveSlot(bookStack)].getItem();
    }

    public ItemStack getActiveItemStack(ItemStack bookStack){
        ItemStack[] inventoryItems = getMyInventory(bookStack);
        if (inventoryItems[getActiveSlot(bookStack)].isEmpty()){
            return ItemStack.EMPTY;
        }
        return inventoryItems[getActiveSlot(bookStack)].copy();
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;
            if (playerEntity.isSneaking()) {
                // open GUI
            } else {
                ItemStack currentSpellStack = getActiveItemStack(stack);
                if (currentSpellStack != null){
                    Items.SPELL_ITEM.get().onPlayerStoppedUsing(currentSpellStack, worldIn, entityLiving, timeLeft);
                }
            }
        }
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return ActionResultType.FAIL;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.FAIL;
    }


    public void setActiveSlot(ItemStack itemStack, int slot){
        if (itemStack.getTag() == null){
            itemStack.setTag(new CompoundNBT());
        }
        if (slot < 0) slot = 0;
        if (slot > 7) slot = 7;
        itemStack.getTag().putInt("spellbookactiveslot", slot);

        ItemStack active = getActiveItemStack(itemStack);
        // boolean Soulbound = EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound.effectId, itemStack) > 0;
        /*if (active != null)
            AMEnchantmentHelper.copyEnchantments(active, itemStack);
        if (Soulbound)
            AMEnchantmentHelper.soulbindStack(itemStack);*/
    }

    public int setNextSlot(ItemStack itemStack){
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;

        do{
            newSlot++;
            if (newSlot > 7) newSlot = 0;
            setActiveSlot(itemStack, newSlot);
        }while (getActiveScroll(itemStack) == null && newSlot != slot);
        return slot;
    }

    public int setPrevSlot(ItemStack itemStack){
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;

        do{
            newSlot--;
            if (newSlot < 0) newSlot = 7;
            setActiveSlot(itemStack, newSlot);
        }while (getActiveScroll(itemStack) == null && newSlot != slot);
        return slot;
    }

    public int getActiveSlot(ItemStack itemStack){
        if (itemStack.getTag() == null){
            setActiveSlot(itemStack, 0);
            return 0;
        }
        return itemStack.getTag().getInt("spellbookactiveslot");
    }

    public ItemStack[] readFromStackTagCompound(ItemStack itemStack){
        NonNullList<ItemStack> list = NonNullList.withSize(8*4, ItemStack.EMPTY);
        if (itemStack.getTag() != null)
        ItemStackHelper.loadAllItems(itemStack.getTag(), list);

        return list.toArray(new ItemStack[0]);
    }

    public SpellBookInventory convertToInventory(ItemStack bookStack){
        SpellBookInventory isb = new SpellBookInventory();
        isb.setInventoryContents(getMyInventory(bookStack));
        return isb;
    }

    public ITextComponent getActiveSpellName(ItemStack bookStack){
        ItemStack stack = getActiveItemStack(bookStack);
        /*if (stack == null){
            // return StatCollector.translateToLocal("am2.tooltip.none");
        }*/
        return stack.getDisplayName();
    }

    @Override
    public void addInformation(ItemStack stackIn, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        /* TODO
        SpellItem activeScroll = getActiveScroll(stackIn);
        ItemStack stack = GetActiveItemStack(stackIn);

        // String s = StatCollector.translateToLocal("am2.tooltip.open");
        tooltip.add((new StringBuilder()).append("\2477").append(s).toString());

        if (activeScroll != null){
            activeScroll.addInformation(stack, par2EntityPlayer, par3List, par4);
        }

        tooltip.add("\247c" + StatCollector.translateToLocal("am2.tooltip.spellbookWarning1") + "\247f");
        tooltip.add("\247c" + StatCollector.translateToLocal("am2.tooltip.spellbookWarning2") + "\247f");*/
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        ItemStack scrollStack = getActiveItemStack(stack);
        if (scrollStack != null){
            // TODO ItemsCommonProxy.spell.onUsingTick(scrollStack, player, count);
        }
    }

    @Override
    public int getItemEnchantability(){
        return 1;
    }
}
