package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
    private static final VoxelShape[] SHAPE = makeRotatedShapes(new double[][]{
            {0, 0, 0, 16, 1, 16},
            {1.5, 1, 1.5, 14.5, 2, 14.5},
            {3, 2, 3, 13, 3, 13},
            {4.5, 3, 4.5, 11.5, 4, 11.5},
            {6, 4, 6, 10, 8, 10},
            {5, 7, 6, 6, 9, 10},
            {11, 7, 10, 10, 9, 6},
            {6, 7, 5, 10, 9, 6},
            {10, 7, 11, 6, 9, 10},
            {9, 8, 12, 7, 10, 11},
            {12, 8, 9, 11, 10, 7},
            {4, 8, 7, 5, 10, 9},
            {7, 8, 4, 9, 10, 5},
            {5, 11, 6.5, 11, 12, 9.5},
            {5, 14, 6.5, 11, 15, 9.5},
            {5, 12, 6.5, 6.5, 14, 9.5},
            {9.5, 12, 6.5, 11, 14, 9.5},
            {4, 11, 7.5, 12, 15, 8.5},
            {5, 10, 7.5, 11, 16, 8.5},
            {6.5, 12, 8.5, 9.5, 14, 9.5},
    });

    public OcculusBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3, 5).harvestLevel(-1).harvestTool(ToolType.PICKAXE));
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    private static VoxelShape[] makeRotatedShapes(double[][] shape) {
        List<Integer> angles = Arrays.asList(0, 90, 180, 270);
        VoxelShape[] shapes = new VoxelShape[4];
        double centerX = 8;
        double centerZ = 8;
        for (int i = 0; i < angles.size(); i++) {
            double angle = Math.toRadians(angles.get(i));
            for (double[] part : shape) {
                double oldX1 = part[0];
                double oldZ1 = part[2];
                double oldX2 = part[3];
                double oldZ2 = part[5];
                double newX1 = centerX + (oldX1 - centerX) * Math.cos(angle) - (oldZ1 - centerZ) * Math.sin(angle);
                double newZ1 = centerZ + (oldX1 - centerX) * Math.sin(angle) + (oldZ1 - centerZ) * Math.cos(angle);
                double newX2 = centerX + (oldX2 - centerX) * Math.cos(angle) - (oldZ2 - centerZ) * Math.sin(angle);
                double newZ2 = centerZ + (oldX2 - centerX) * Math.sin(angle) + (oldZ2 - centerZ) * Math.cos(angle);
                if (shapes[i] == null) shapes[i] = makeCuboidShape(newX1, part[1], newZ1, newX2, part[4], newZ2);
                else
                    shapes[i] = VoxelShapes.combineAndSimplify(shapes[i], makeCuboidShape(newX1, part[1], newZ1, newX2, part[4], newZ2), IBooleanFunction.OR);
            }
        }
        return shapes;
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
            Minecraft.getInstance().displayGuiScreen(new OcculusScreen(new TranslationTextComponent(ArsMagicaAPI.MODID + ".occulus.displayname"), player));
        }
        return ActionResultType.SUCCESS;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE[(state.get(FACING).getHorizontalIndex() + 2) % 4];
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
