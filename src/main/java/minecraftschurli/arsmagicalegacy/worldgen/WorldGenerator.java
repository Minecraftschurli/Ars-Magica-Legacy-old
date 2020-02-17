package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.simpleorelib.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.*;
import net.minecraftforge.registries.*;

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
                        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModFeatures.SUNSTONE_ORE.get().withConfiguration(new OreFeatureConfig(SimpleOreLib.fillerForBiome(biome), ModBlocks.SUNSTONE_ORE.get().getDefaultState(), 1)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 25))));
                    }
                    if (BiomeFilter.OVERWORLD.apply(biome)) {
                        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.MOONSTONE_METEOR.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
                        if (BiomeFilter.SANDY.apply(biome)) {
                            //biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.DESERT_NOVA.get().withConfiguration(NoFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(2))));
                        } else {
                            //biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.FLOWERS.get().getRandomFlower() IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(1)));
                            biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModFluids.LIQUID_ESSENCE_BLOCK.lazyMap(Block::getDefaultState).get())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(200))));
                        }
                    }
                });
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }
}
