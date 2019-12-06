package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Minecraftschurli
 * @version 2019-10-15
 */
public class OreBase extends Block {
    public static final List<OreBase> ORES = new ArrayList<>();

    private BiomeFilter biomeFilter;
    private int veinSize;
    private int chance;
    private int minHeight;
    private int maxHeight;

    public OreBase(Properties properties, OreSpawnProperties oreProperties) {
        super(properties);

        this.veinSize = oreProperties.veinSize;
        this.chance = oreProperties.chance;
        this.minHeight = oreProperties.minHeight;
        this.maxHeight = oreProperties.maxHeight;
        this.biomeFilter = oreProperties.biomeFilter;

        ORES.add(this);
    }

    public int getVeinSize() {
        return veinSize;
    }

    public int getChance() {
        return chance;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public boolean hasBiomeFilter() {
        return this.biomeFilter != BiomeFilter.ALL;
    }

    public BiomeFilter getBiomeFilter() {
        return biomeFilter;
    }

    public static class OreSpawnProperties {
        private BiomeFilter biomeFilter;
        private int veinSize;
        private int chance;
        private int minHeight;
        private int maxHeight;

        private OreSpawnProperties(int veinSize, int chance, int minHeight, int maxHeight) {
            this.veinSize = veinSize;
            this.chance = chance;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
            this.biomeFilter = BiomeFilter.ALL;
        }

        public static OreSpawnProperties create(int veinSize, int chance, int minHeight, int maxHeight) {
            return new OreSpawnProperties(veinSize, chance, minHeight, maxHeight);
        }

        public OreSpawnProperties addFilter(BiomeFilter filter) {
            this.biomeFilter = new BiomeFilter(this.biomeFilter.biomes.toArray(new Biome[0]), filter.biomes.toArray(new Biome[0]));
            return this;
        }
    }

    public static class BiomeFilter implements Predicate<Biome> {
        public static final BiomeFilter ALL = new BiomeFilter();
        public static final BiomeFilter BADLANDS = new BiomeFilter(
                Biomes.BADLANDS,
                Biomes.BADLANDS_PLATEAU,
                Biomes.ERODED_BADLANDS,
                Biomes.MODIFIED_BADLANDS_PLATEAU,
                Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU,
                Biomes.WOODED_BADLANDS_PLATEAU
        );

        public static final BiomeFilter OCEAN = new BiomeFilter(
                Biomes.OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_WARM_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN
        );

        public static final BiomeFilter MOUNTAINS = new BiomeFilter(
                Biomes.MOUNTAINS,
                Biomes.SNOWY_MOUNTAINS,
                Biomes.WOODED_MOUNTAINS,
                Biomes.GRAVELLY_MOUNTAINS,
                Biomes.TAIGA_MOUNTAINS,
                Biomes.SNOWY_TAIGA_MOUNTAINS,
                Biomes.MODIFIED_GRAVELLY_MOUNTAINS
        );

        private final List<Biome> biomes;

        BiomeFilter(Biome... biomes) {
            this.biomes = Arrays.asList(biomes);
        }

        private BiomeFilter(Biome[] biomes1, Biome[] biomes2) {
            this.biomes = Arrays.asList(biomes1);
            this.biomes.addAll(Arrays.asList(biomes2));
        }

        @Override
        public boolean test(Biome biome) {
            if (this == ALL) return true;
            return this.biomes.contains(biome);
        }
    }
}
