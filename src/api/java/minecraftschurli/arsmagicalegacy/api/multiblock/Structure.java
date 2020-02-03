package minecraftschurli.arsmagicalegacy.api.multiblock;

import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.state.properties.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-16
 */
@SuppressWarnings("UnstableApiUsage")
@ParametersAreNonnullByDefault
public class Structure {

    private final ImmutableList<ImmutableList<ImmutableList<Supplier<BlockState>>>> layers;

    /**
     * Creates a new {@link Structure}
     *
     * @param layers the layers for the {@link Structure}
     * @throws IllegalArgumentException if the a given layer is malformed
     */
    @SafeVarargs
    public Structure(Supplier<BlockState>[][]... layers) throws IllegalArgumentException {
        int size = layers[0].length;
        this.layers = Arrays.stream(layers).map(layer -> {
            if (layer.length != size)
                throw new IllegalArgumentException("Layers in a Structure must all have the same size!");
            int size2 = layer[0].length;
            return Arrays.stream(layer).map(row -> {
                if (row.length != size2)
                    throw new IllegalArgumentException("Layers in a Structure must all have the same size!");
                return ImmutableList.copyOf(row);
            }).collect(ImmutableList.toImmutableList());
        }).collect(ImmutableList.toImmutableList());
    }

    /**
     * Checks the {@link Structure} against the {@link World} world starting at {@link BlockPos} pos and facing in {@link Direction} direction
     *
     * @param world     the {@link World} to check the structure
     * @param start     the {@link BlockPos} to start at
     * @param direction the {@link Direction} the structure should face
     * @return true if the structure is valid
     */
    public boolean check(World world, BlockPos start, Direction direction) {
        for (int y = 0; y < layers.size(); y++) {
            ImmutableList<ImmutableList<Supplier<BlockState>>> rows = layers.get(y);
            for (int z = 0; z < rows.size(); z++) {
                ImmutableList<Supplier<BlockState>> blocks = rows.get(z);
                for (int x = 0; x < blocks.size(); x++) {
                    BlockState state = blocks.get(x).get();
                    BlockPos pos = start.up(y).offset(direction.getOpposite(), z).offset(direction.rotateY(), x);
                    if (!checkState(state, world, pos, direction)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean checkState(@Nullable BlockState block, World world, BlockPos pos, Direction direction) {
        if (block == null)
            return true;
        if (block.has(BlockStateProperties.HORIZONTAL_FACING)) {
            return world.getBlockState(pos).equals(block.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex((block.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex()))));
        } else if (block.getBlock() instanceof AirBlock) {
            return world.getBlockState(pos).isAir(world, pos);
        } else {
            return block.equals(world.getBlockState(pos));
        }
    }

    /**
     * Places the structure in the given world facing the given direction starting at BlockPos pos
     *
     * @param world     the {@link World} to place the structure in
     * @param pos       the {@link BlockPos} to start at
     * @param direction the {@link Direction} the structure should face
     */
    public void place(World world, BlockPos pos, Direction direction) {
        for (int y = 0; y < layers.size(); y++) {
            ImmutableList<ImmutableList<Supplier<BlockState>>> rows = layers.get(y);
            for (int z = 0; z < rows.size(); z++) {
                ImmutableList<Supplier<BlockState>> blocks = rows.get(z);
                for (int x = 0; x < blocks.size(); x++) {
                    BlockState state = blocks.get(x).get();
                    BlockPos p = pos.up(y).offset(direction.getOpposite(), z).offset(direction.rotateY(), x);
                    if (state.has(BlockStateProperties.HORIZONTAL_FACING)) {
                        world.setBlockState(p, state.with(BlockStateProperties.HORIZONTAL_FACING, Direction.byHorizontalIndex(state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalIndex() + direction.getHorizontalIndex())));
                    } else {
                        world.setBlockState(p, state);
                    }
                }
            }
        }
    }
}
