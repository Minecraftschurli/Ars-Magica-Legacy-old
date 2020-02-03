package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author IchHabeHunger54
 */
public class InfinityOrbItem extends Item {
    public static String TYPE_KEY = "type";

    public InfinityOrbItem() {
        super(ModItems.ITEM_64);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().sorted(Comparator.comparingInt(SkillPoint::getTier)).collect(Collectors.toList())) {
                if (!type.canRender()) continue;
                ItemStack stack = new ItemStack(this);
                stack.getOrCreateTag().putInt(TYPE_KEY, type.getTier());
                items.add(stack);
            }
        }
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        if (worldIn.isRemote || !playerIn.getHeldItem(handIn).hasTag() || !playerIn.getHeldItem(handIn).getTag().contains(TYPE_KEY) || playerIn.getHeldItem(handIn).getTag().getInt(TYPE_KEY) < 0)
            return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        return useOrb(playerIn, playerIn.getHeldItem(handIn));
    }

    public ItemStack setSkillPoint(SkillPoint point) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt(TYPE_KEY, point.getTier());
        return stack;
    }

    public static SkillPoint getSkillPoint(ItemStack stack) {
        return SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(TYPE_KEY));
    }

    /*@Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return useOrb(context.getPlayer(), stack).getType();
    }*/

    private ActionResult<ItemStack> useOrb(LivingEntity entity, ItemStack heldItem) {
        if (entity instanceof PlayerEntity){
            CapabilityHelper.addSkillPoint((PlayerEntity) entity, heldItem.getTag().getInt(TYPE_KEY));
            heldItem.shrink(1);
        }
        return ActionResult.newResult(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent(this.getTranslationKey(stack), getSkillPoint(stack).getDisplayName());
    }

    /*@Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null) return ActionResultType.FAIL;
        return this.useOrb(context.getPlayer(), context.getItem()).getType();
    }*/
}
