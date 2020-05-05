package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorBlock;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.api.IMultiblock;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class BlackAuremBlock extends EtheriumGeneratorBlock<BlackAuremTileEntity> {
    public static final Supplier<IMultiblock> BLACK_AUREM_CHALK = PatchouliCompat.registerMultiblock("black_aurem_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"   ", " B ", "   "},
                    {"CCC", "C0C", "CCC"}},
            'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_1 = PatchouliCompat.registerMultiblock("black_aurem_pillar1", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"G   G", "     ", "     ", "     ", "G   G"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GOLD_BLOCK),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_2 = PatchouliCompat.registerMultiblock("black_aurem_pillar2", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"D   D", "     ", "     ", "     ", "D   D"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'D', iPatchouliAPI.strictBlockMatcher(Blocks.DIAMOND_BLOCK),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));
    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_3 = PatchouliCompat.registerMultiblock("black_aurem_pillar3", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"H   H", "     ", "     ", "     ", "H   H"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'H', iPatchouliAPI.strictBlockMatcher(ModBlocks.CHIMERITE_BLOCK.get()),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));

    public BlackAuremBlock() {
        super(Properties.create(Material.ROCK).notSolid(), ModTileEntities.BLACK_AUREM);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getAmbientOcclusionLightValue(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getTier(BlockState state, World world, BlockPos pos) {
        int tier = 0;
        if (BLACK_AUREM_CHALK.get().validate(world, pos) != null) {
            if (BLACK_AUREM_PILLAR_1.get().validate(world, pos) != null) {
                tier = 2;
            } else if (BLACK_AUREM_PILLAR_2.get().validate(world, pos) != null) {
                tier = 3;
            } else if (BLACK_AUREM_PILLAR_3.get().validate(world, pos) != null) {
                tier = 4;
            } else {
                tier = 1;
            }
        }
        return tier;
    }
}
