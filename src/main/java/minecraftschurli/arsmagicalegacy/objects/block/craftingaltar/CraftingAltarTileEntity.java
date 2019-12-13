package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarTileEntity extends TileEntity {
    private static final LinkedHashMap<Block, Integer> CAPS = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, Integer> MAIN = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, StairsBlock> STAIRS = new LinkedHashMap<>();
    private Block cap;
    private Block main;

    public CraftingAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public boolean checkMultiblock(World world){
        BlockPos pos = getPos();
        BlockPos basePos = pos.offset(Direction.DOWN, 4);

        // get and check cap block
        Block bottomCenter = world.getBlockState(basePos).getBlock();
        if (!CAPS.containsKey(bottomCenter))
            return false;
        this.cap = bottomCenter;

        // get and check main block
        Block firstMain = world.getBlockState(basePos.north()).getBlock();
        if (!MAIN.containsKey(firstMain))
            return false;
        this.main = firstMain;

        // check base
        BlockPos cornerPos = basePos.add(-2,0,-2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2)
                    continue;
                if(main != world.getBlockState(cornerPos.add(i,0,j)).getBlock())
                    return false;
            }
        }

        // get and check axis
        Direction.Axis direction = getDrection(world);
        if (direction == null)
            return false;

        // check blocks beside Altar
        if (main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction))).getBlock() ||
            main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, direction))).getBlock())
            return false;

        // check stairs 1
        BlockState stair = STAIRS.get(main).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.SHAPE, StairsShape.STRAIGHT);
        Direction d = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction);
        if (!world.getBlockState(pos.offset(d, 2)).equals(stair.with(StairsBlock.FACING, d.getOpposite())))
            return false;

        // check stairs 2
        d = Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, direction);
        if (!world.getBlockState(pos.offset(d, 2)).equals(stair.with(StairsBlock.FACING, d.getOpposite())))
            return false;

        // check stairs 3 Todo check
        Direction d2 = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction).rotateY();
        BlockPos stairsPos1 = pos.offset(d2).offset(d);
        BlockPos stairsPos2 = pos.offset(d2.getOpposite()).offset(d);
        for (int i = 0; i < 3; i++) {
            if (!world.getBlockState(stairsPos1.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2.getOpposite())) ||
                !world.getBlockState(stairsPos2.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2)))
                return false;
        }

        // Todo check walls


        return true;
    }

    private Direction.Axis getDrection(World world) {
        BlockPos pos = getPos();
        if (world.isAirBlock(pos.offset(Direction.NORTH, 2)))
            return Direction.Axis.X;
        else if (world.isAirBlock(pos.offset(Direction.WEST, 2)))
            return Direction.Axis.Z;
        else return null;
    }
}
