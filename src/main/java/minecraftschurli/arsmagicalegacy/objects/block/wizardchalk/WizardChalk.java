package minecraftschurli.arsmagicalegacy.objects.block.wizardchalk;

import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class WizardChalk extends Block {
    private static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 15);
//    private static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);
    private static final Random rand = new Random();

    public WizardChalk(Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(TYPE, 0)/*.with(FACING, Direction.NORTH)*/);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.create(0.125, 0, 0.125, 0.875, 0.02, 0.875);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(TYPE, rand.nextInt(16))/*.with(FACING, context.getPlacementHorizontalFacing())*/;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(ModItems.WIZARD_CHALK.get());
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE/*, FACING*/);
    }
}
