package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.util.BlockUtil;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class OcculusBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SOCKET = BlockUtil.combineShapes(makeCuboidShape(0, 0, 0, 16, 1, 16), makeCuboidShape(1.5, 1, 1.5, 14.5, 2, 14.5), makeCuboidShape(3, 2, 3, 13, 3, 13), makeCuboidShape(4.5, 3, 4.5, 11.5, 4, 11.5), makeCuboidShape(6, 4, 6, 10, 8, 10), makeCuboidShape(5, 7, 6, 6, 9, 10), makeCuboidShape(11, 7, 10, 10, 9, 6), makeCuboidShape(6, 7, 5, 10, 9, 6), makeCuboidShape(10, 7, 11, 6, 9, 10), makeCuboidShape(9, 8, 12, 7, 10, 11), makeCuboidShape(12, 8, 9, 11, 10, 7), makeCuboidShape(4, 8, 7, 5, 10, 9), makeCuboidShape(7, 8, 4, 9, 10, 5));
    private static final VoxelShape NORTH = BlockUtil.combineShapes(makeCuboidShape(5, 11, 6.5, 11, 12, 9.5), makeCuboidShape(5, 14, 6.5, 11, 15, 9.5), makeCuboidShape(5, 12, 6.5, 6.5, 14, 9.5), makeCuboidShape(9.5, 12, 6.5, 11, 14, 9.5), makeCuboidShape(4, 11, 7.5, 12, 15, 8.5), makeCuboidShape(5, 10, 7.5, 11, 16, 8.5), makeCuboidShape(6.5, 12, 8.5, 9.5, 14, 9.5));
    private static final VoxelShape EAST = BlockUtil.combineShapes(makeCuboidShape(6.5, 11, 11, 9.5, 12, 5), makeCuboidShape(6.5, 14, 11, 9.5, 15, 5), makeCuboidShape(6.5, 12, 9.5, 9.5, 14, 11), makeCuboidShape(6.5, 12, 5, 9.5, 14, 6.5), makeCuboidShape(7.5, 11, 12, 8.5, 15, 4), makeCuboidShape(7.5, 10, 11, 8.5, 16, 5), makeCuboidShape(7.5, 12, 9.5, 6.5, 14, 6.5));
    private static final VoxelShape SOUTH = BlockUtil.combineShapes(makeCuboidShape(5, 10, 7.5, 11, 16, 8.5), makeCuboidShape(4, 11, 7.5, 12, 15, 8.5), makeCuboidShape(5, 11, 6.5, 11, 12, 9.5), makeCuboidShape(5, 14, 6.5, 11, 15, 9.5), makeCuboidShape(5, 12, 6.5, 6.5, 14, 9.5), makeCuboidShape(9.5, 12, 6.5, 11, 14, 9.5), makeCuboidShape(6.5, 12, 6.5, 9.5, 14, 7.5));
    private static final VoxelShape WEST = BlockUtil.combineShapes(makeCuboidShape(6.5, 11, 11, 9.5, 12, 5), makeCuboidShape(6.5, 14, 11, 9.5, 15, 5), makeCuboidShape(6.5, 12, 6.5, 9.5, 14, 5), makeCuboidShape(6.5, 12, 11, 9.5, 14, 9.5), makeCuboidShape(7.5, 11, 12, 8.5, 15, 4), makeCuboidShape(7.5, 10, 11, 8.5, 16, 5), makeCuboidShape(8.5, 12, 9.5, 9.5, 14, 6.5));

    public OcculusBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3, 5).harvestTool(ToolType.PICKAXE));
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isSecondaryUseActive())
            return ActionResultType.PASS;
        if (worldIn.isRemote) {
            if (CapabilityHelper.getCurrentLevel(player) == 0 && !player.isCreative()) {
                player.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".occulus.prevent"));
                return ActionResultType.FAIL;
            }
            RenderUtil.displayOcculusScreen(player);
        }
        return ActionResultType.SUCCESS;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.combineAndSimplify(SOCKET, state.get(FACING) == Direction.NORTH ? NORTH : state.get(FACING) == Direction.EAST ? EAST : state.get(FACING) == Direction.SOUTH ? SOUTH : WEST, IBooleanFunction.OR);
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
