package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

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
        this.setSkillPoint(stack, ModSpellParts.SILVER_POINT);
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
        if (worldIn.isRemote || playerIn.getHeldItem(handIn).getTag().getInt(TYPE_KEY) < 0)
            return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        return useOrb(playerIn, playerIn.getHeldItem(handIn));
    }

    public ItemStack setSkillPoint(ItemStack stack, SkillPoint point) {
        stack.getOrCreateTag().putInt(TYPE_KEY, point.getTier());
        return stack;
    }

    public SkillPoint getSkillPoint(ItemStack stack) {
        return SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(TYPE_KEY));
    }

    /*@Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return useOrb(context.getPlayer(), stack).getType();
    }*/

    private ActionResult<ItemStack> useOrb(LivingEntity entity, ItemStack heldItem) {
        MagicHelper.addSkillPoint(entity, heldItem.getTag().getInt(TYPE_KEY));
        heldItem.shrink(1);
        return ActionResult.newResult(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent(this.getTranslationKey(stack), this.getSkillPoint(stack).getDisplayName());
    }

    /*@Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null) return ActionResultType.FAIL;
        return this.useOrb(context.getPlayer(), context.getItem()).getType();
    }*/
}
