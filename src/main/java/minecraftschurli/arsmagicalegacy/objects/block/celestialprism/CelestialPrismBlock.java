package minecraftschurli.arsmagicalegacy.objects.block.celestialprism;

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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.api.IMultiblock;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class CelestialPrismBlock extends EtheriumGeneratorBlock<CelestialPrismTileEntity> {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final Supplier<IMultiblock> CELESTIAL_PRISM_CHALK = PatchouliCompat.registerMultiblock("celestial_prism_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]
                            {{"   ", " P ", "   "},
                                    {"CCC", "C0C", "CCC"}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_1 = PatchouliCompat.registerMultiblock("celestial_prism_pillar1", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]
                            {{"G   G", "     ", "     ", "     ", "G   G"},
                                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GLASS),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_2 = PatchouliCompat.registerMultiblock("celestial_prism_pillar2", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]
                            {{"G   G", "     ", "     ", "     ", "G   G"},
                                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GOLD_BLOCK),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_3 = PatchouliCompat.registerMultiblock("celestial_prism_pillar3", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]
                            {{"D   D", "     ", "     ", "     ", "D   D"},
                                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'D', iPatchouliAPI.strictBlockMatcher(Blocks.DIAMOND_BLOCK),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_4 = PatchouliCompat.registerMultiblock("celestial_prism_pillar4", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]
                            {{"M   M", "     ", "     ", "     ", "M   M"},
                                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'M', iPatchouliAPI.strictBlockMatcher(ModBlocks.MOONSTONE_BLOCK.get()),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));

    public CelestialPrismBlock() {
        super(Properties.create(Material.GLASS).notSolid(), ModTileEntities.CELESTIAL_PRISM);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getAmbientOcclusionLightValue(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return 1;
    }

    @Override
    public boolean propagatesSkylightDown(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf)
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        else return Blocks.AIR.getDefaultState();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) ? this.getDefaultState() : null;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack) {
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    public boolean isValidPosition(BlockState state, @Nonnull IWorldReader worldIn, @Nonnull BlockPos pos) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) return super.isValidPosition(state, worldIn, pos);
        else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos);
            return blockstate.getBlock() == this && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public void harvestBlock(@Nonnull World worldIn, @Nonnull PlayerEntity player, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable TileEntity te, @Nonnull ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    @Override
    public void onBlockHarvested(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        DoubleBlockHalf doubleblockhalf = state.get(HALF);
        BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        TileEntity te = worldIn.getTileEntity(doubleblockhalf == DoubleBlockHalf.LOWER ? pos : blockpos);
        if (blockstate.getBlock() == this && blockstate.get(HALF) != doubleblockhalf) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            if (!worldIn.isRemote && !player.isCreative()) {
                spawnDrops(state, worldIn, pos, te, player, player.getHeldItemMainhand());
                spawnDrops(blockstate, worldIn, blockpos, te, player, player.getHeldItemMainhand());
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public int getTier(BlockState state, World world, BlockPos pos) {
        int tier = 0;
        if (CELESTIAL_PRISM_CHALK.get().validate(world, pos) != null) {
            if (CELESTIAL_PRISM_PILLAR_1.get().validate(world, pos) != null) tier = 2;
            else if (CELESTIAL_PRISM_PILLAR_2.get().validate(world, pos) != null) tier = 3;
            else if (CELESTIAL_PRISM_PILLAR_3.get().validate(world, pos) != null) tier = 4;
            else if (CELESTIAL_PRISM_PILLAR_4.get().validate(world, pos) != null) tier = 5;
            else tier = 1;
        }
        return tier;
    }
}
