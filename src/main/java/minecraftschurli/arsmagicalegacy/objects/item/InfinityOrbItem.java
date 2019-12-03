package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.SpellParts;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import minecraftschurli.arsmagicalegacy.util.SpellRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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
        stack.getOrCreateTag().putInt(TYPE_KEY, SpellParts.SILVER_POINT.getTier());
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (SkillPoint type : SpellRegistry.SKILL_POINT_REGISTRY) {
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
        if (worldIn.isRemote) return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        return useOrb(playerIn, playerIn.getHeldItem(handIn));
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
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack)+"."+stack.getTag().getInt(TYPE_KEY);
    }

    /*@Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null) return ActionResultType.FAIL;
        return this.useOrb(context.getPlayer(), context.getItem()).getType();
    }*/
}
