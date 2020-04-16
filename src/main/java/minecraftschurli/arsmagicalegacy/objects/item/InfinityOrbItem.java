package minecraftschurli.arsmagicalegacy.objects.item;

import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.registry.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * @author IchHabeHunger54
 */
public class InfinityOrbItem extends Item {
    public static String TYPE_KEY = "type";

    public InfinityOrbItem() {
        super(ModItems.ITEM_64);
    }

    public static ItemStack getWithSkillPoint(SkillPoint point) {
        return ModItems.INFINITY_ORB.get().setSkillPoint(new ItemStack(ModItems.INFINITY_ORB.get()), point);
    }

    public static SkillPoint getSkillPoint(ItemStack stack) {
        return SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(TYPE_KEY));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (SkillPoint type : SkillPointRegistry.SKILL_POINT_REGISTRY.values().stream().sorted(Comparator.comparingInt(SkillPoint::getTier)).collect(Collectors.toList())) {
                if (!type.canRender()) continue;
                items.add(setSkillPoint(new ItemStack(this), type));
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

    public ItemStack setSkillPoint(ItemStack stack, SkillPoint point) {
        if (!(stack.getItem() instanceof InfinityOrbItem))
            return stack;
        stack.getOrCreateTag().putInt(TYPE_KEY, point.getTier());
        return stack;
    }

    private ActionResult<ItemStack> useOrb(LivingEntity entity, ItemStack heldItem) {
        if (entity instanceof PlayerEntity) {
            CapabilityHelper.addSkillPoint((PlayerEntity) entity, heldItem.getTag().getInt(TYPE_KEY));
            heldItem.shrink(1);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent(this.getTranslationKey(stack), getSkillPoint(stack).getDisplayName());
    }
}
