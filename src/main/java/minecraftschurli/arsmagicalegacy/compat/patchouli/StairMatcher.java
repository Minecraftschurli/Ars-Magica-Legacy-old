package minecraftschurli.arsmagicalegacy.compat.patchouli;

import minecraftschurli.arsmagicalegacy.api.config.CraftingAltarStructureMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.util.TriPredicate;
import vazkii.patchouli.api.IStateMatcher;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Minecraftschurli
 * @version 2020-04-17
 */
public class StairMatcher implements IStateMatcher {
    private final Direction facing;
    private final Half half;
    private final TriPredicate<IBlockReader, BlockPos, BlockState> predicte;

    public StairMatcher(Direction facing, Half half){
        this.facing = facing;
        this.half = half;
        predicte = (iBlockReader, blockPos, blockState) -> blockState.getBlock() instanceof StairsBlock && blockState.get(StairsBlock.HALF) == this.half && blockState.get(StairsBlock.FACING) == this.facing;
    }

    /**
     * Gets the state displayed by this state matcher for rendering
     * the multiblock page type and the in-world preview.
     *
     * @param ticks World ticks, to allow cycling the state shown.
     */
    @Nonnull
    @Override
    public BlockState getDisplayedState(int ticks) {
        List<Block> main = new ArrayList<>(CraftingAltarStructureMaterials.getAllMainBlocks());
        return CraftingAltarStructureMaterials.getStairForBlock(main.get((ticks/20)%main.size())).getDefaultState().with(StairsBlock.FACING, this.facing).with(StairsBlock.HALF, this.half);
    }

    /**
     * Returns a predicate that validates whether the given state is
     * acceptable. This should check the passed in blockstate instead of requerying it from the world,
     * for both performance and correctness reasons -- the state may be rotated for multiblock matching.
     */
    @Nonnull
    @Override
    public TriPredicate<IBlockReader, BlockPos, BlockState> getStatePredicate() {
        return predicte;
    }
}
