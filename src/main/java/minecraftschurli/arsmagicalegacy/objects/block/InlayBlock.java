package minecraftschurli.arsmagicalegacy.objects.block;

import java.util.function.Predicate;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public class InlayBlock extends AbstractRailBlock {
    public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class, ((Predicate<RailShape>)RailShape::isAscending).negate());

    public InlayBlock(Block.Properties properties) {
        super(false, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SHAPE, RailShape.NORTH_SOUTH));
    }

    protected void updateState(BlockState state, World world, BlockPos pos, Block block) {
        int rails = 0;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (AbstractRailBlock.isRail(world, pos.offset(direction))) {
                ++rails;
            }
        }

        if (block.getDefaultState().canProvidePower() && rails == 3) {
            this.getUpdatedState(world, pos, state, false);
        }
    }

    @Override
    public IProperty<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case CLOCKWISE_180:
                switch(state.get(SHAPE)) {
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                }
            case COUNTERCLOCKWISE_90:
                switch(state.get(SHAPE)) {
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_SOUTH:
                        return state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return state.with(SHAPE, RailShape.NORTH_SOUTH);
                }
            case CLOCKWISE_90:
                switch(state.get(SHAPE)) {
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_SOUTH:
                        return state.with(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return state.with(SHAPE, RailShape.NORTH_SOUTH);
                }
            default:
                return state;
        }
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        RailShape railshape = state.get(SHAPE);
        switch(mirrorIn) {
            case LEFT_RIGHT:
                switch(railshape) {
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(state, mirrorIn);
                }
            case FRONT_BACK:
                switch(railshape) {
                    case SOUTH_EAST:
                        return state.with(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.with(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.with(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return state.with(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(state, mirrorIn);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SHAPE);
    }
}
