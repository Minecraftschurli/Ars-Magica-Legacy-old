package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import minecraftschurli.arsmagicalegacy.api.multiblock.Structure;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarTileEntity extends TileEntity {
    private static final LinkedHashMap<Block, Integer> CAPS = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, Integer> MAIN = new LinkedHashMap<>();
    private static final LinkedHashMap<Block, StairsBlock> STAIRS = new LinkedHashMap<>();
    private static final Supplier<BlockState> AIR = Blocks.AIR::getDefaultState;
    private static final Supplier<BlockState> WALL = ModBlocks.MAGIC_WALL.lazyMap(Block::getDefaultState);
    private static final Supplier<BlockState> LECTERN = Blocks.LECTERN::getDefaultState;
    private static final Supplier<BlockState> LEVER = Blocks.LEVER::getDefaultState;
    private static final Supplier<BlockState> ALTAR = ModBlocks.ALTAR_CORE.lazyMap(Block::getDefaultState);
    private AtomicReference<BlockState> cap = new AtomicReference<>();
    private AtomicReference<BlockState> main = new AtomicReference<>();
    private Supplier<BlockState> stairBottom1 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.EAST);
    private Supplier<BlockState> stairBottom2 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, Direction.WEST);
    private Supplier<BlockState> stairBottom3 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.NORTH);
    private Supplier<BlockState> stairBottom4 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.SOUTH);
    private Supplier<BlockState> stairBottom5 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.EAST);
    private Supplier<BlockState> stairBottom6 = () -> STAIRS.get(main.get().getBlock()).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.FACING, Direction.WEST);
    private final Structure STRUCTURE = new Structure(
            new Supplier[][]{
                    {main::get,main::get,main::get,main::get,main::get},
                    {main::get,main::get,main::get,main::get,main::get},
                    {main::get,main::get,cap::get,main::get,main::get},
                    {main::get,main::get,main::get,main::get,main::get},
                    {main::get,main::get,main::get,main::get,main::get}
            },
            new Supplier[][]{
                    {AIR,AIR,AIR,AIR,AIR},
                    {main::get,AIR,AIR,AIR,main::get},
                    {WALL,AIR,AIR,AIR,WALL},
                    {main::get,AIR,AIR,AIR,main::get},
                    {AIR,AIR,AIR,AIR,LECTERN}
            },
            new Supplier[][]{
                    {AIR,AIR,AIR,AIR,AIR},
                    {main::get,AIR,AIR,AIR,main::get},
                    {WALL,AIR,AIR,AIR,WALL},
                    {main::get,AIR,AIR,AIR,main::get},
                    {AIR,AIR,AIR,AIR,LEVER}
            },
            new Supplier[][]{
                    {AIR,AIR,AIR,AIR,AIR},
                    {main::get,stairBottom1,AIR,stairBottom2,main::get},
                    {WALL,AIR,AIR,AIR,WALL},
                    {main::get,stairBottom1,AIR,stairBottom2,main::get},
                    {AIR,AIR,AIR,AIR,AIR}
            },
            new Supplier[][]{
                    {AIR,AIR,AIR,AIR,AIR},
                    {cap::get,stairBottom3,stairBottom3,stairBottom3,cap::get},
                    {stairBottom6,main::get,ALTAR,main::get,stairBottom5},
                    {cap::get,stairBottom4,stairBottom4,stairBottom4,cap::get},
                    {AIR,AIR,AIR,AIR,AIR}
            }
    );

    static {
        CAPS.put(ModBlocks.SUNSTONE_BLOCK.get(), 2);
        MAIN.put(Blocks.PURPUR_BLOCK, 2);
        STAIRS.put(Blocks.PURPUR_BLOCK, (StairsBlock) Blocks.PURPUR_STAIRS);
    }

    public CraftingAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        main.set(Blocks.PURPUR_BLOCK.getDefaultState());
        cap.set(ModBlocks.SUNSTONE_BLOCK.lazyMap(Block::getDefaultState).get());
    }

    public CraftingAltarTileEntity() {
        this(ModTileEntities.ALTAR_CORE.get());
    }

    public boolean checkMultiblock(World world){
        BlockPos pos = getPos();
        BlockPos basePos = pos.offset(Direction.DOWN, 4);

        // get and check cap block
        Block bottomCenter = world.getBlockState(basePos).getBlock();
        if (!CAPS.containsKey(bottomCenter))
            return false;
        this.cap.set(bottomCenter.getDefaultState());

        // get and check main block
        Block firstMain = world.getBlockState(basePos.north()).getBlock();
        if (!MAIN.containsKey(firstMain))
            return false;
        this.main.set(firstMain.getDefaultState());

        Direction direction = getStructureDirection(world, pos);
        if (direction == null) return false;
        return STRUCTURE.check(world, pos.down(4).offset(direction, 2).offset(direction.rotateYCCW(), 2), direction);
        /*
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

        // check blocks beside Altar Core
        if (main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction))).getBlock() ||
            main != world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, direction))).getBlock())
            return false;

        // check stairs 1
        BlockState stair = STAIRS.get(main).getDefaultState().with(StairsBlock.HALF, Half.BOTTOM).with(StairsBlock.SHAPE, StairsShape.STRAIGHT);
        Direction d = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, direction);
        if (!world.getBlockState(pos.offset(d.getOpposite(), 2)).equals(stair.with(StairsBlock.FACING, d)) ||
            !world.getBlockState(pos.offset(d, 2)).equals(stair.with(StairsBlock.FACING, d.getOpposite())))
            return false;

        // check stairs 2 Todo test
        Direction d2 = d.rotateY();
        BlockPos stairsPos1 = pos.offset(d2).offset(d);
        BlockPos stairsPos2 = pos.offset(d2.getOpposite()).offset(d);
        for (int i = 0; i < 3; i++) {
            if (!world.getBlockState(stairsPos1.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2.getOpposite())) ||
                    !world.getBlockState(stairsPos2.offset(d.getOpposite(), i)).equals(stair.with(StairsBlock.FACING, d2)))
                return false;
        }

        // check arch stairs
        if (!world.getBlockState(pos.down().offset(d).offset(d.rotateY())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d.getOpposite())) ||
                !world.getBlockState(pos.down().offset(d).offset(d.rotateYCCW())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d.getOpposite())) ||
                !world.getBlockState(pos.down().offset(d.getOpposite()).offset(d.rotateY())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d)) ||
                !world.getBlockState(pos.down().offset(d.getOpposite()).offset(d.rotateYCCW())).equals(stair.with(StairsBlock.HALF, Half.TOP).with(StairsBlock.FACING, d)))
            return false;*/

        // Todo check walls


    }

    public void placeStructure(World world, Direction face) {
        if (face.getAxis().isVertical())
            return;
        STRUCTURE.place(world, getPos(), face);
    }

    private Direction getStructureDirection(World world, BlockPos pos) {
        Direction.Axis axis = getDirection(world, pos);
        if (world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis), 2).offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis).rotateYCCW(), 2).down(3)).getBlock() == Blocks.LECTERN) {
            return Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis);
        } else if (world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis), 2).offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis).rotateYCCW(), 2).down(3)).getBlock() == Blocks.LECTERN) {
            return Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis);
        } else {
            return null;
        }
    }

    private Direction.Axis getDirection(World world, BlockPos pos) {
        if (world.isAirBlock(pos.offset(Direction.NORTH, 2)))
            return Direction.Axis.Z;
        else if (world.isAirBlock(pos.offset(Direction.WEST, 2)))
            return Direction.Axis.X;
        else return null;
    }

}
