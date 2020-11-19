package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.arsmagicalegacy.objects.fluid.LiquidEssenceFluid;
import minecraftschurli.simpleorelib.BiomeFilter;
import minecraftschurli.simpleorelib.SimpleOreLib;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WorldGenerator {
    public static void setupModFeatures() {
        ForgeRegistries.BIOMES.getValues().forEach(biome -> {
            if (BiomeFilter.NETHER.apply(biome) && Config.SERVER.SUNSTONE_VEIN_SIZE.get() > 0)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.SUNSTONE_ORE.get().withConfiguration(new OreFeatureConfig(SimpleOreLib.fillerForBiome(biome), ModBlocks.SUNSTONE_ORE.get().getDefaultState(), Config.SERVER.SUNSTONE_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 25))));
            if (BiomeFilter.OVERWORLD.apply(biome)) {
                if (Config.SERVER.METEOR_FREQUENCY.get() > 0)
                    biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.MOONSTONE_METEOR.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(Config.SERVER.METEOR_FREQUENCY.get()))));
                if (BiomeFilter.SANDY.apply(biome)) {
                    if (Config.SERVER.DESERT_NOVA_ENABLED.get())
                        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.DESERT_NOVA.lazyMap(Block::getDefaultState).get()), new SimpleBlockPlacer()).tries(4).build()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
                } else {
                    if (Config.SERVER.OTHER_FLOWERS_ENABLED.get())
                        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(ModBlocks.AUM.lazyMap(Block::getDefaultState).get(), 1).addWeightedBlockstate(ModBlocks.CERUBLOSSOM.lazyMap(Block::getDefaultState).get(), 1).addWeightedBlockstate(ModBlocks.TARMA_ROOT.lazyMap(Block::getDefaultState).get(), 1).addWeightedBlockstate(ModBlocks.WAKEBLOOM.lazyMap(Block::getDefaultState).get(), 1), new SimpleBlockPlacer()).tries(16).build()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
                    if (Config.SERVER.LAKE_FREQUENCY.get() > 0)
                        biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModFluids.LIQUID_ESSENCE_BLOCK.lazyMap(FlowingFluidBlock::getDefaultState).get())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(Config.SERVER.LAKE_FREQUENCY.get()))));
                }
            }
        });
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }
}
