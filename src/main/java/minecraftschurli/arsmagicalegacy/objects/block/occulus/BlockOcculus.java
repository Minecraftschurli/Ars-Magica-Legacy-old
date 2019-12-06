package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import minecraftschurli.arsmagicalegacy.util.MagicHelper;
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
import net.minecraft.util.BlockRenderLayer;
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

import javax.annotation.Nullable;
import java.util.Arrays;

public class BlockOcculus extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = makeShape(
            makeCuboidShape(0, 0, 0, 16, 1, 16),
            makeCuboidShape(1.5, 1, 1.5, 14.5, 2, 14.5),
            makeCuboidShape(3, 2, 3, 13, 3, 13),
            makeCuboidShape(4.5, 3, 4.5, 11.5, 4, 11.5),
            makeCuboidShape(6, 4, 6, 10, 8, 10),
            makeCuboidShape(5, 7, 6, 6, 9, 10),
            makeCuboidShape(11, 7, 10, 10, 9, 6),
            makeCuboidShape(6, 7, 5, 10, 9, 6),
            makeCuboidShape(10, 7, 11, 6, 9, 10),
            makeCuboidShape(9, 8, 12, 7, 10, 11),
            makeCuboidShape(12, 8, 9, 11, 10, 7),
            makeCuboidShape(4, 8, 7, 5, 10, 9),
            makeCuboidShape(7, 8, 4, 9, 10, 5),
            makeCuboidShape(5, 11, 6.5, 11, 12, 9.5),
            makeCuboidShape(5, 14, 6.5, 11, 15, 9.5),
            makeCuboidShape(5, 12, 6.5, 6.5, 14, 9.5),
            makeCuboidShape(9.5, 12, 6.5, 11, 14, 9.5),
            makeCuboidShape(4, 11, 7.5, 12, 15, 8.5),
            makeCuboidShape(5, 10, 7.5, 11, 16, 8.5),
            makeCuboidShape(6.5, 12, 8.5, 9.5, 14, 9.5)
    ).simplify();

    public BlockOcculus() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 5.0F).harvestLevel(-1).harvestTool(ToolType.PICKAXE));
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    private static VoxelShape makeShape(VoxelShape... shapes) {
        return Arrays.stream(shapes).reduce((voxelShape, voxelShape2) -> VoxelShapes.combine(voxelShape, voxelShape2, IBooleanFunction.OR)).get();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState()/*.with(FACING, context.getPlayer().getHorizontalFacing().getOpposite())*/;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isSneaking())
            return false;
        if (worldIn.isRemote) {
            if (MagicHelper.getCurrentLevel(player) == 0 && !player.isCreative()) {
                player.sendMessage(new TranslationTextComponent("occulus.prevent"));//"Mythical forces prevent you from using this device!"
                return true;
            }
            Minecraft.getInstance().displayGuiScreen(new OcculusScreen(new TranslationTextComponent("occulus.displayname"), player));
        }
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
