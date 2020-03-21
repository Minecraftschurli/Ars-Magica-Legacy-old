package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.simpleorelib.BiomeFilter;
import minecraftschurli.simpleorelib.SimpleOreLib;
import net.minecraft.block.Block;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
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
        ForgeRegistries.BIOMES.getValues()
                .forEach(biome -> {
                    if (BiomeFilter.NETHER.apply(biome)){
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.SUNSTONE_ORE.get().withConfiguration(new OreFeatureConfig(SimpleOreLib.fillerForBiome(biome), ModBlocks.SUNSTONE_ORE.get().getDefaultState(), 1)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 25))));
                    }
                    if (BiomeFilter.OVERWORLD.apply(biome)) {
                        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.MOONSTONE_METEOR.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
                        if (BiomeFilter.SANDY.apply(biome)) {
                            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.DESERT_NOVA.lazyMap(Block::getDefaultState).get()), new SimpleBlockPlacer()).tries(4).build()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(2))));
                        } else {
                            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(
                                    new WeightedBlockStateProvider()
                                            .func_227407_a_(ModBlocks.AUM.lazyMap(Block::getDefaultState).get(), 1)
                                            .func_227407_a_(ModBlocks.CERUBLOSSOM.lazyMap(Block::getDefaultState).get(), 1)
                                            .func_227407_a_(ModBlocks.TARMA_ROOT.lazyMap(Block::getDefaultState).get(), 1)
                                            .func_227407_a_(ModBlocks.WAKEBLOOM.lazyMap(Block::getDefaultState).get(), 1),
                                    new SimpleBlockPlacer()).tries(16).build()).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
                            biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModFluids.LIQUID_ESSENCE_BLOCK.lazyMap(Block::getDefaultState).get())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(200))));
                        }
                    }
                });
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }
}
