package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Function;

/**
 * @author IchHabeHunger54
 */
public class ResearchOrbItem extends Item {
    private OrbTypes orbTypes;
    public ResearchOrbItem(OrbTypes orbTypes) {
        super(ModItems.ITEM_64);
        this.orbTypes = orbTypes;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        return addResearchPoints(playerIn.getHeldItem(handIn))
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    protected boolean addResearchPoints(OrbTypes orbTypes) {
        return true;
    }

    public enum OrbTypes {
        BLUE((iResearchPointStorage, stack) -> {
            iResearchPointStorage.addBlue();
            return ActionResult.newResult(ActionResultType.SUCCESS, stack)
        }),
        GREEN,
        RED;

        private final BiFunction<IResearchPointStorage, ItemStack, ActionResult<ItemStack>> useFunction;

        OrbTypes(BiFunction<IResearchPointStorage, ItemStack, ActionResult<ItemStack>> use){
            this.useFunction = use;
        }
    }
}
