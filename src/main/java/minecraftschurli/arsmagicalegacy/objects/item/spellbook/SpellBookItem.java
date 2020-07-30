package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.objects.item.spell.SpellItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
@SuppressWarnings("unused")
public class SpellBookItem extends Item implements IDyeableArmorItem {
    public SpellBookItem() {
        super(new Item.Properties().maxStackSize(1).group(ArsMagicaLegacy.ITEM_GROUP).setISTER(() -> SpellBookISTER::new));
    }

    public static List<ItemStack> getActiveInventory(ItemStack itemStack) {
        return getInventory(itemStack).stream().limit(8).collect(Collectors.toList());
    }

    private static NonNullList<ItemStack> getInventory(ItemStack itemStack) {
        return readFromStackTagCompound(itemStack);
    }

    public static Optional<SpellItem> getActiveScroll(ItemStack bookStack) {
        return Optional.of(getInventory(bookStack).get(getActiveSlot(bookStack)))
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::getItem)
                .map(item -> (SpellItem) item);
    }

    public static ItemStack getActiveItemStack(ItemStack bookStack) {
        return Optional.of(getInventory(bookStack).get(getActiveSlot(bookStack)))
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::copy)
                .orElse(ItemStack.EMPTY);
    }

    public static void setActiveSlot(ItemStack itemStack, int slot) {
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

    public static int setNextSlot(ItemStack itemStack) {
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;
        do {
            newSlot++;
            if (newSlot > 7) newSlot = 0;
            setActiveSlot(itemStack, newSlot);
        } while (!getActiveScroll(itemStack).isPresent() && newSlot != slot);
        return slot;
    }

    public static int setPrevSlot(ItemStack itemStack) {
        int slot = getActiveSlot(itemStack);
        int newSlot = slot;
        do {
            newSlot--;
            if (newSlot < 0) newSlot = 7;
            setActiveSlot(itemStack, newSlot);
        } while (!getActiveScroll(itemStack).isPresent() && newSlot != slot);
        return slot;
    }

    public static int getActiveSlot(ItemStack itemStack) {
        if (itemStack.getTag() == null) {
            setActiveSlot(itemStack, 0);
            return 0;
        }
        return itemStack.getTag().getInt("spellbookactiveslot");
    }

    public static NonNullList<ItemStack> readFromStackTagCompound(ItemStack itemStack) {
        NonNullList<ItemStack> list = NonNullList.withSize(8 * 4, ItemStack.EMPTY);
        if (itemStack.getTag() != null)
            ItemStackHelper.loadAllItems(itemStack.getTag(), list);
        return list;
    }

    public static ITextComponent getActiveSpellName(ItemStack bookStack) {
        ItemStack stack = getActiveItemStack(bookStack);
        return stack.getDisplayName();
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (CapabilityHelper.getCurrentLevel(player) <= 0 && !player.isCreative())
            return ActionResult.resultPass(stack);
        if (player.isSneaking()) {
            if (!world.isRemote && player instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                    @Override
                    public Container createMenu(int id, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player) {
                        return new SpellBookContainer(id, playerInventory, new SpellBookInventory(getInventory(stack)));
                    }

                    @Nonnull
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

    @Nonnull
    @Override
    public UseAction getUseAction(@Nonnull ItemStack itemstack) {
        if (getUseDuration(itemstack) == 0) {
            return UseAction.NONE;
        }
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return getActiveScroll(stack)
                .map(spellItem -> spellItem.getUseDuration(stack))
                .orElse(0);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stackIn, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        Optional<SpellItem> activeScroll = getActiveScroll(stackIn);
        ItemStack stack = getActiveItemStack(stackIn);
        tooltip.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".tooltip.open"));
        activeScroll.ifPresent(spellItem -> spellItem.addInformation(stack, worldIn, tooltip, flagIn));
        tooltip.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".spellBook.warning.0"));
        tooltip.add(new TranslationTextComponent(ArsMagicaAPI.MODID + ".spellBook.warning.1"));
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
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving, int timeLeft) {
        getActiveScroll(stack).ifPresent(spellItem -> spellItem.onPlayerStoppedUsing(getActiveItemStack(stack), worldIn, entityLiving, timeLeft));
    }
}
