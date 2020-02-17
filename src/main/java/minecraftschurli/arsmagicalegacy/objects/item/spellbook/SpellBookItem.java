package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.objects.item.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

import static minecraftschurli.arsmagicalegacy.init.ModItems.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class SpellBookItem extends Item implements IDyeableArmorItem {
    public SpellBookItem() {
        super(ITEM_1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (player.func_226563_dT_()) {
            if (!world.isRemote && player instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                    @Override
                    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
                        return new SpellBookContainer(id, playerInventory, new SpellBookInventory(getInventory(stack)));
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return stack.getDisplayName();
                    }
                });
            }
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            return getActiveScroll(stack)
                    .map(spellItem -> spellItem.onItemRightClick(world, player, hand))
                    .orElse(new ActionResult<>(ActionResultType.FAIL, stack));
        }
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        if (getUseDuration(itemstack) == 0) {
            return UseAction.NONE;
        }
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return getActiveScroll(stack)
                .map(spellItem -> spellItem.getUseDuration(stack))
                .orElse(0);
    }

    public List<ItemStack> getActiveInventory(ItemStack itemStack) {
        return getInventory(itemStack).stream().limit(8).collect(Collectors.toList());
    }

    private NonNullList<ItemStack> getInventory(ItemStack itemStack) {
        return readFromStackTagCompound(itemStack);
    }

    public Optional<SpellItem> getActiveScroll(ItemStack bookStack) {
        return Optional.of(getInventory(bookStack).get(getActiveSlot(bookStack)))
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::getItem)
                .map(item -> (SpellItem) item);
    }

    public ItemStack getActiveItemStack(ItemStack bookStack) {
        return Optional.of(getInventory(bookStack).get(getActiveSlot(bookStack)))
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::copy)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        /*final PlayerEntity player = context.getPlayer();
        final Hand hand = context.getHand();
        final World world = context.getWorld();
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
                return ActionResultType.SUCCESS;
            }
        } else {
            return getActiveScroll(stack)
                    .map(spellItem -> spellItem.onItemUse(context))
                    .orElse(ActionResultType.FAIL);
        }
        return ActionResultType.FAIL;*/
        return ActionResultType.PASS;
    }


    public void setActiveSlot(ItemStack itemStack, int slot) {
        if (itemStack.getTag() == null) {
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

    public int setNextSlot(ItemStack itemStack) {
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;

        do {
            newSlot++;
            if (newSlot > 7) newSlot = 0;
            setActiveSlot(itemStack, newSlot);
        } while (!getActiveScroll(itemStack).isPresent() && newSlot != slot);
        return slot;
    }

    public int setPrevSlot(ItemStack itemStack) {
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;

        do {
            newSlot--;
            if (newSlot < 0) newSlot = 7;
            setActiveSlot(itemStack, newSlot);
        } while (!getActiveScroll(itemStack).isPresent() && newSlot != slot);
        return slot;
    }

    public int getActiveSlot(ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            setActiveSlot(itemStack, 0);
            return 0;
        }
        return itemStack.getTag().getInt("spellbookactiveslot");
    }

    public NonNullList<ItemStack> readFromStackTagCompound(ItemStack itemStack) {
        NonNullList<ItemStack> list = NonNullList.withSize(8 * 4, ItemStack.EMPTY);
        if (itemStack.getTag() != null)
            ItemStackHelper.loadAllItems(itemStack.getTag(), list);

        return list;
    }

    public ITextComponent getActiveSpellName(ItemStack bookStack) {
        ItemStack stack = getActiveItemStack(bookStack);
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
        getActiveScroll(stack).ifPresent(spellItem -> spellItem.onUsingTick(getActiveItemStack(stack), player, count));
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        getActiveScroll(stack).ifPresent(spellItem -> spellItem.onPlayerStoppedUsing(getActiveItemStack(stack), worldIn, entityLiving, timeLeft));
    }
}
