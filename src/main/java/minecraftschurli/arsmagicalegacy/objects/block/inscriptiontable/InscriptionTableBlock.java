package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import minecraftschurli.arsmagicalegacy.objects.item.InscriptionTableUpgradeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
@ParametersAreNonnullByDefault
public class InscriptionTableBlock extends Block {
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<InscriptionTableBlock.Half> HALF = EnumProperty.create("half", Half.class);

    public InscriptionTableBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2, 2).lightValue(1).notSolid());
        setDefaultState(getDefaultState().with(TIER, 0).with(FACING, Direction.NORTH).with(HALF, Half.RIGHT));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TIER, FACING, HALF);
    }

    @Nonnull
    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (state.get(HALF) != Half.LEFT) {
            return blockstate.isSolidSide(worldIn, blockpos, Direction.UP);
        } else {
            return blockstate.getBlock() == this;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.offset(state.get(FACING).rotateY()), state.with(HALF, Half.LEFT), 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getWorld().getBlockState(context.getPos().offset(context.getPlacementHorizontalFacing().getOpposite().rotateY())).isReplaceable(context))
            return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
        return null;
    }

    /*public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos ceurrentPos, BlockPos facingPos) {
        boolean doubleblockhalf = stateIn.get(HALF);
        if (facing.getAxis() == Direction.Axis.Y && (!doubleblockhalf) == (facing == Direction.UP)) {
            return facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf ? stateIn.with(FACING, facingState.get(FACING)) : Blocks.AIR.getDefaultState();
        } else {
            return !doubleblockhalf && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }*/

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean doubleblockhalf = state.get(HALF) == Half.LEFT;
        BlockPos blockpos = !doubleblockhalf ? pos.offset(state.get(FACING).rotateY()) : pos.offset(state.get(FACING).rotateYCCW());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (blockstate.getBlock() == this && (blockstate.get(HALF) == Half.LEFT) != doubleblockhalf) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            ItemStack itemstack = player.getHeldItemMainhand();
            if (!worldIn.isRemote && !player.isCreative()) {
                BlockPos tePos = state.get(HALF) == Half.LEFT ? pos.offset(state.get(FACING).rotateY()) : pos;
                InscriptionTableTileEntity te = (InscriptionTableTileEntity) worldIn.getTileEntity(tePos);
                Block.spawnDrops(state, worldIn, pos, te, player, itemstack);
                Block.spawnDrops(blockstate, worldIn, blockpos, te, player, itemstack);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(HALF) != Half.LEFT;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        if (worldIn instanceof World)
            return new InscriptionTableTileEntity((World) worldIn);
        return new InscriptionTableTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        super.onBlockActivated(state, worldIn, pos, player, hand, hit);
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        boolean left = state.get(HALF) == Half.LEFT;
        BlockPos tePos = left ? pos.offset(state.get(FACING).rotateYCCW()) : pos;
        InscriptionTableTileEntity te = (InscriptionTableTileEntity) worldIn.getTileEntity(tePos);
        if (te == null)
            return ActionResultType.FAIL;
        if (te.isInUse(player)) {
            player.sendMessage(new StringTextComponent("Someone else is using this."));
            return ActionResultType.FAIL;
        }
        ItemStack curItem = player.getHeldItem(hand);
        if (!curItem.isEmpty() && curItem.getItem() instanceof InscriptionTableUpgradeItem && curItem.getTag() != null) {
            int tier = state.get(TIER);
            if (tier == InscriptionTableUpgradeItem.getTier(curItem)) {
                curItem.shrink(1);
                worldIn.setBlockState(pos, state.with(TIER, tier + 1), 2);
                BlockPos other = left ? pos.offset(state.get(FACING).rotateYCCW()) : pos.offset(state.get(FACING).rotateY());
                worldIn.setBlockState(other, worldIn.getBlockState(other).with(TIER, tier + 1), 2);
                te.incrementUpgradeState();
                return ActionResultType.SUCCESS;
            }
        } else {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Nonnull
                @Override
                public ITextComponent getDisplayName() {
                    return new StringTextComponent("");
                }

                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return new InscriptionTableContainer(p_createMenu_1_, p_createMenu_2_, te);
                }
            }, tePos);
        }
        return ActionResultType.SUCCESS;
    }

    public enum Half implements IStringSerializable {
        LEFT, RIGHT;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
