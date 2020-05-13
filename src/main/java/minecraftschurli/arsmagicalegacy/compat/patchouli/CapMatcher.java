package minecraftschurli.arsmagicalegacy.compat.patchouli;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.config.CraftingAltarStructureMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.util.TriPredicate;
import vazkii.patchouli.api.IStateMatcher;

/**
 * @author Minecraftschurli
 * @version 2020-04-17
 */
public class CapMatcher implements IStateMatcher {
    private final TriPredicate<IBlockReader, BlockPos, BlockState> predicate = (iBlockReader, blockPos, blockState) -> CraftingAltarStructureMaterials.getAllCaps().stream().map(Block::getDefaultState).anyMatch(blockState::equals);

    /**
     * Gets the state displayed by this state matcher for rendering
     * the multiblock page type and the in-world preview.
     *
     * @param ticks World ticks, to allow cycling the state shown.
     */
    @Nonnull
    @Override
    public BlockState getDisplayedState(int ticks) {
        List<Block> caps = new ArrayList<>(CraftingAltarStructureMaterials.getAllCaps());
        return caps.get((ticks / 20) % caps.size()).getDefaultState();
    }

    /**
     * Returns a predicate that validates whether the given state is
     * acceptable. This should check the passed in blockstate instead of requerying it from the world,
     * for both performance and correctness reasons -- the state may be rotated for multiblock matching.
     */
    @Nonnull
    @Override
    public TriPredicate<IBlockReader, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
