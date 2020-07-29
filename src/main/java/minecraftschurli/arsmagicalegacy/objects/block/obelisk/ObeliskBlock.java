package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorBlock;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import vazkii.patchouli.api.IMultiblock;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class ObeliskBlock extends EtheriumGeneratorBlock<ObeliskTileEntity> {
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final Supplier<IMultiblock> OBELISK_CHALK = PatchouliCompat.registerMultiblock("obelisk_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                            {"   ", " U ", "   "},
                            {"   ", " M ", "   "},
                            {"CCC", "C0C", "CCC"}},
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState()),
                    'M', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, Part.MIDDLE)),
                    'U', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, Part.UPPER)),
                    'C', PatchouliCompat.CHALK_MATCHER.get())
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> OBELISK_PILLARS = PatchouliCompat.registerMultiblock("obelisk_pillars", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                            {"S   S", "     ", "  U  ", "     ", "S   S"},
                            {"B   B", "     ", "  M  ", "     ", "B   B"},
                            {"B   B", " CCC ", " C0C ", " CCC ", "B   B"}},
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState()),
                    'M', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, Part.MIDDLE)),
                    'U', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, Part.UPPER)),
                    'S', iPatchouliAPI.looseBlockMatcher(Blocks.CHISELED_STONE_BRICKS),
                    'B', iPatchouliAPI.looseBlockMatcher(Blocks.STONE_BRICKS),
                    'C', PatchouliCompat.CHALK_MATCHER.get())
                    .setSymmetrical(true));
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ObeliskBlock() {
        super(Properties.create(Material.ROCK).notSolid(), ModTileEntities.OBELISK);
        setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(PART, Part.LOWER));
    }

    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        Part half = stateIn.get(PART);
        if (facing.getAxis() != Direction.Axis.Y || half == Part.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.get(PART) != half)
            return half == Part.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return Blocks.AIR.getDefaultState();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) ? this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()) : null;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(PART, Part.MIDDLE).with(FACING, state.get(FACING)), 3);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(PART, Part.UPPER).with(FACING, state.get(FACING)), 3);
    }

    public boolean isValidPosition(BlockState state, @Nonnull IWorldReader worldIn, @Nonnull BlockPos pos) {
        if (state.get(PART) == Part.LOWER) return super.isValidPosition(state, worldIn, pos);
        else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos);
            return blockstate.getBlock() == this && (blockstate.get(PART) == Part.LOWER || blockstate.get(PART) == Part.MIDDLE);
        }
    }

    @Override
    public void harvestBlock(@Nonnull World worldIn, @Nonnull PlayerEntity player, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable TileEntity te, @Nonnull ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        Part doubleblockhalf = state.get(PART);
        BlockPos blockpos1 = (doubleblockhalf == Part.MIDDLE || doubleblockhalf == Part.LOWER) ? pos.up() : pos.down();
        BlockPos blockpos2 = (doubleblockhalf == Part.MIDDLE || doubleblockhalf == Part.UPPER) ? pos.down() : pos.up();
        BlockState blockstate1 = worldIn.getBlockState(blockpos1);
        BlockState blockstate2 = worldIn.getBlockState(blockpos2);
        TileEntity te = worldIn.getTileEntity(doubleblockhalf == Part.LOWER ? pos : blockpos2);
        if (blockstate1.getBlock() == this && blockstate1.get(PART) != doubleblockhalf) {
            worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos1, Block.getStateId(blockstate1));
            if (!worldIn.isRemote && !player.isCreative()) {
                spawnDrops(state, worldIn, pos, te, player, player.getHeldItemMainhand());
                spawnDrops(blockstate1, worldIn, blockpos1, te, player, player.getHeldItemMainhand());
            }
        }
        if (blockstate2.getBlock() == this && blockstate2.get(PART) != doubleblockhalf) {
            worldIn.setBlockState(blockpos2, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos2, Block.getStateId(blockstate2));
            if (!worldIn.isRemote && !player.isCreative()) {
                spawnDrops(state, worldIn, pos, te, player, player.getHeldItemMainhand());
                spawnDrops(blockstate2, worldIn, blockpos2, te, player, player.getHeldItemMainhand());
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (worldIn.isRemote) return ActionResultType.SUCCESS;
        TileEntity te = worldIn.getTileEntity(state.get(PART) == Part.LOWER ? pos : state.get(PART) == Part.MIDDLE ? pos.down() : pos.down(2));
        if (!(te instanceof INamedContainerProvider)) return ActionResultType.FAIL;
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, state.get(PART) == Part.LOWER ? pos : state.get(PART) == Part.MIDDLE ? pos.down() : pos.down(2));
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(PART) == Part.LOWER;
    }

    @Override
    public int getTier(BlockState state, World world, BlockPos pos) {
        int tier = 0;
        if (OBELISK_CHALK.get().validate(world, pos) != null) {
            tier = 1;
            if (OBELISK_PILLARS.get().validate(world, pos) != null) tier = 2;
        }
        return tier;
    }

    public enum Part implements IStringSerializable {
        UPPER, MIDDLE, LOWER;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
