package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.simpleorelib.BiomeFilter;
import minecraftschurli.simpleorelib.SimpleOreLib;
import net.minecraft.block.Block;
import net.minecraft.world.gen.GenerationStage;
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
@SuppressWarnings("ConstantConditions")
public class WorldGenerator {

    public static void setupOregen() {
        ForgeRegistries.BIOMES.getValues()
                .forEach(biome -> {
                    if (BiomeFilter.NETHER.apply(biome)){
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.SUNSTONE_ORE.get().withConfiguration(new OreFeatureConfig(SimpleOreLib.fillerForBiome(biome), ModBlocks.SUNSTONE_ORE.get().getDefaultState(), 1)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(1, 0, 0, 25))));
                    }
                    if (BiomeFilter.OVERWORLD.apply(biome)) {
                        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.MOONSTONE_METEOR.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(1))));
                        if (BiomeFilter.SANDY.apply(biome)) {
                            //biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.DESERT_NOVA.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
                        } else {
                            //biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.FLOWERS.get().getRandomFlower() IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(1)));
                            biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModFluids.LIQUID_ESSENCE_BLOCK.lazyMap(Block::getDefaultState).get())).func_227228_a_(Placement.WATER_LAKE.func_227446_a_(new ChanceConfig(200))));
                        }
                    }
                });
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }
}
