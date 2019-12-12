package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import minecraftschurli.arsmagicalegacy.init.ModItems;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class InscriptionTableBlock extends Block {
    static final IntegerProperty TIER = IntegerProperty.create("tier", 1, 3);
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty LEFT = BooleanProperty.create("left");

    public InscriptionTableBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 2.0f).lightValue(1));
        setDefaultState(getDefaultState().with(TIER, 1).with(FACING, Direction.NORTH).with(LEFT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TIER, FACING, LEFT);
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (!state.get(LEFT)) {
            return blockstate.func_224755_d(worldIn, blockpos, Direction.UP);
        } else {
            return blockstate.getBlock() == this;
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.offset(state.get(FACING).rotateY()), state.with(LEFT, true), 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getWorld().getBlockState(context.getPos().offset(context.getPlacementHorizontalFacing().getOpposite().rotateY())).isReplaceable(context))
            return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
        return null;
    }

    /*public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos ceurrentPos, BlockPos facingPos) {
        boolean doubleblockhalf = stateIn.get(LEFT);
        if (facing.getAxis() == Direction.Axis.Y && (!doubleblockhalf) == (facing == Direction.UP)) {
            return facingState.getBlock() == this && facingState.get(LEFT) != doubleblockhalf ? stateIn.with(FACING, facingState.get(FACING)) : Blocks.AIR.getDefaultState();
        } else {
            return !doubleblockhalf && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }*/

    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean doubleblockhalf = state.get(LEFT);
        BlockPos blockpos = !doubleblockhalf ? pos.offset(state.get(FACING).rotateY()) : pos.offset(state.get(FACING).rotateYCCW());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.get(LEFT) != doubleblockhalf) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            ItemStack itemstack = player.getHeldItemMainhand();
            if (!worldIn.isRemote && !player.isCreative()) {
                BlockPos tePos = state.get(LEFT) ? pos.offset(state.get(FACING).rotateY()) : pos;
                InscriptionTableTileEntity te = (InscriptionTableTileEntity) worldIn.getTileEntity(tePos);
                Block.spawnDrops(state, worldIn, pos, te, player, itemstack);
                Block.spawnDrops(blockstate, worldIn, blockpos, te, player, itemstack);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return !state.get(LEFT);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
        if (worldIn instanceof World)
            return new InscriptionTableTileEntity((World)worldIn);
        return new InscriptionTableTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        super.onBlockActivated(state, worldIn, pos, player, hand, hit);
        if (worldIn.isRemote) {
            return true;
        }
        BlockPos tePos = state.get(LEFT) ? pos.offset(state.get(FACING).rotateYCCW()) : pos;
        InscriptionTableTileEntity te = (InscriptionTableTileEntity) worldIn.getTileEntity(tePos);

        if (te == null)
            return true;

        if (te.isInUse(player)) {
            player.sendMessage(new StringTextComponent("Someone else is using this."));
            return true;
        }

        ItemStack curItem = player.getHeldItem(hand);
        if (!curItem.isEmpty() && curItem.getItem() == ModItems.INSCRIPTION_UPGRADE.get()) {
            if (te.getUpgradeState() == curItem.getTag().getInt("tier")) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                te.incrementUpgradeState();
                te.incrementUpgradeState();
                return true;
            }
        }
        NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new StringTextComponent("");
            }

            @Override
            public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                return new InscriptionTableContainer(p_createMenu_1_, p_createMenu_2_, te);
            }
        }, tePos);

        return true;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
