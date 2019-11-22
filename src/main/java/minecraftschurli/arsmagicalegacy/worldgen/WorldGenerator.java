package minecraftschurli.arsmagicalegacy.worldgen;

import minecraftschurli.arsmagicalegacy.init.ModBiomes;
import minecraftschurli.arsmagicalegacy.objects.block.OreBase;
import minecraftschurli.arsmagicalegacy.worldgen.structures.MoonstoneMeteor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Stream;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WorldGenerator {
    public static void setupOregen() {
        Stream<OreBase> ores = OreBase.ORES.stream();
        Stream<Biome> biomes = ForgeRegistries.BIOMES.getValues().stream();
        ores.forEach(ore -> {
                    Stream<Biome> biomes1 = biomes;
                    if (ore.hasBiomeFilter())
                        biomes1 = biomes1.filter(ore.getBiomeFilter());
                    biomes1.forEach(biome -> biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(isNether(biome) ? OreFeatureConfig.FillerBlockType.NETHERRACK : OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore.getDefaultState(), ore.getVeinSize()), Placement.COUNT_RANGE, new CountRangeConfig(ore.getChance(), ore.getMinHeight(), ore.getMinHeight(), ore.getMaxHeight()))));
                }
        );
        biomes.filter(WorldGenerator::isOverworld).forEach(biome -> biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(new MoonstoneMeteor(NoFeatureConfig::deserialize), NoFeatureConfig.NO_FEATURE_CONFIG, Placement.CHANCE_HEIGHTMAP, new ChanceConfig(1))));
    }

    public static void setupBiomeGen() {
        OverworldBiomeProvider.BIOMES_TO_SPAWN_IN.add(ModBiomes.WITCHWOOD_FOREST.get());
    }

    private static boolean isNether(Biome biome) {
        return biome.getCategory() == Biome.Category.NETHER;
    }

    private static boolean isTheEnd(Biome biome) {
        return biome.getCategory() == Biome.Category.THEEND;
    }

    private static boolean isOverworld(Biome biome) {
        return !isNether(biome) && !isTheEnd(biome);
    }
}
