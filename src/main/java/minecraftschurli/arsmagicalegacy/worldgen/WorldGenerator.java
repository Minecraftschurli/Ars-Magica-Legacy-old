package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.objects.block.OreBase;
import minecraftschurli.arsmagicalegacy.worldgen.structures.MoonstoneMeteor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Stream;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WorldGenerator {
    public static void setupOregen() {
        Stream<OreBase> ores = OreBase.ORES.stream();

        ores.forEach(ore -> {
                    Stream<Biome> biomes = ForgeRegistries.BIOMES.getValues().stream();
                    if (ore.hasBiomeFilter())
                        biomes = biomes.filter(ore.getBiomeFilter());
                    biomes.forEach(biome -> biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(isNether(biome) ? OreFeatureConfig.FillerBlockType.NETHERRACK : OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore.getDefaultState(), ore.getVeinSize()), Placement.COUNT_RANGE, new CountRangeConfig(ore.getChance(), ore.getMinHeight(), ore.getMinHeight(), ore.getMaxHeight()))));
                }
        );
        Stream<Biome> biomes = ForgeRegistries.BIOMES.getValues().stream();
        biomes.filter(WorldGenerator::isOverworld).forEach(biome -> biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(new MoonstoneMeteor(NoFeatureConfig::deserialize), NoFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP, new FrequencyConfig(1))));
    }

    public static void setupBiomeGen() {
        BiomeDictionary.addTypes(ModBiomes.WITCHWOOD_FOREST.get(), BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
    }

    private static boolean isNether(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER);
    }

    private static boolean isTheEnd(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.END);
    }

    private static boolean isOverworld(Biome biome) {
        return BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD);
    }
}
