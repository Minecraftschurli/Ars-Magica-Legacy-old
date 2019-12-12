package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if (context.getPos().getY() < 255 && context.getWorld().getBlockState(context.getPos().offset(context.getPlayer().getHorizontalFacing().getOpposite().rotateAround(Direction.Axis.Y))).isReplaceable(context))
            return getDefaultState().with(FACING, context.getPlayer().getHorizontalFacing().getOpposite());
        return null;
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

        InscriptionTableTileEntity te = (InscriptionTableTileEntity) worldIn.getTileEntity(pos);

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
        }, pos);

        return true;
    }
}
