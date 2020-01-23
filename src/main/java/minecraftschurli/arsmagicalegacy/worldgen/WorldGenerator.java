package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import minecraftschurli.simpleorelib.BiomeFilter;
import minecraftschurli.simpleorelib.SimpleOreLib;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
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
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(ModFeatures.SUNSTONE_ORE.get(), new OreFeatureConfig(SimpleOreLib.fillerForBiome(biome), ModBlocks.SUNSTONE_ORE.get().getDefaultState(), 1), Placement.COUNT_RANGE, new CountRangeConfig(1, 0, 0, 25)));
                    }
                    if (BiomeFilter.OVERWORLD.apply(biome)) {
                        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ModFeatures.MOONSTONE_METEOR.get(), NoFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP, new FrequencyConfig(1)));
                        if (BiomeFilter.SANDY.apply(biome)) {
                            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ModFeatures.DESERT_NOVA.get(), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(2)));
                        } else {
                            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ModFeatures.FLOWERS.get(), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(1)));
                            biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(ModFluids.LIQUID_ESSENCE_BLOCK.get().getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(200)));
                        }
                    }
                });
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }
}
