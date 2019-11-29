package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.capabilities.research.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import javax.annotation.*;

/**
 * @author IchHabeHunger54
 */
public class InfinityOrbItem extends Item {
    private static String TYPE_KEY = "type";

    public InfinityOrbItem() {
        super(ModItems.ITEM_64);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
        stack.getOrCreateTag().putString(TYPE_KEY, SkillPoint.SILVER_POINT.getName());
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (SkillPoint type : SkillPoint.TYPES) {
                ItemStack stack = new ItemStack(this);
                stack.getOrCreateTag().putString(TYPE_KEY, type.getName());
                items.add(stack);
            }
        }
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        if (worldIn.isRemote) return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        return useOrb(playerIn, playerIn.getHeldItem(handIn));
    }

    private ActionResult<ItemStack> useOrb(PlayerEntity playerIn, ItemStack heldItem) {
        playerIn.getCapability(CapabilityResearch.RESEARCH_POINTS).orElseThrow(() -> new IllegalStateException("No Research Capability Present")).add(heldItem.getTag().getString(TYPE_KEY));
        heldItem.shrink(1);
        return ActionResult.newResult(ActionResultType.SUCCESS, heldItem);
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null) return ActionResultType.FAIL;
        return this.useOrb(context.getPlayer(), context.getItem()).getType();
    }
}
