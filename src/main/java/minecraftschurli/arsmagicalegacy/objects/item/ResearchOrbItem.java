package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchPointsStorage;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.function.Function;

/**
 * @author IchHabeHunger54
 */
public class ResearchOrbItem extends Item {
    private OrbTypes orbType;
    public ResearchOrbItem(OrbTypes orbTypes) {
        super(ModItems.ITEM_64);
        this.orbType = orbTypes;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
        return ActionResult.newResult(this.orbType.use(playerIn), playerIn.getHeldItem(handIn));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return this.orbType.use(context.getPlayer());
    }

    public enum OrbTypes {
        BLUE((iResearchPointStorage) -> {
            iResearchPointStorage.addBlue();
            return ActionResultType.SUCCESS;
        }),
        GREEN((iResearchPointStorage) -> {
            iResearchPointStorage.addGreen();
            return ActionResultType.SUCCESS;
        }),
        RED((iResearchPointStorage) -> {
            iResearchPointStorage.addRed();
            return ActionResultType.SUCCESS;
        });

        private final Function<IResearchPointsStorage, ActionResultType> useFunction;

        OrbTypes(Function<IResearchPointsStorage, ActionResultType> use){
            this.useFunction = use;
        }

        public ActionResultType use(PlayerEntity playerIn) {
            LazyOptional<IResearchPointsStorage> capability = playerIn.getCapability(CapabilityResearch.RESEARCH_POINTS);
            if (capability.isPresent()) {
                return useFunction.apply(capability.orElse(null));
            }
            return ActionResultType.FAIL;
        }
    }
}
