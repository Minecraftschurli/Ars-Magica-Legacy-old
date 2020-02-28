package minecraftschurli.arsmagicalegacy.worldgen.biomes;

import minecraftschurli.arsmagicalegacy.init.ModFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodForestBiome extends Biome implements ICustomFeatureBiome {
    public WitchwoodForestBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .precipitation(RainType.RAIN)
                .category(Category.FOREST)
                .depth(0.1F)
                .scale(0.2F)
                .temperature(0.6F)
                .downfall(0.6F)
                .waterColor(0x0a2a72)
                .waterFogColor(0x092971)
                .parent(null)
        );
        this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
        this.addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addGrass(this);
        DefaultBiomeFeatures.addMushrooms(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
    }

    public void init() {
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.WITCHWOOD_TREE.get().withConfiguration(ModFeatures.WITCHWOOD_TREE_CONFIG.get()).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    }

    @Override
    public int getSkyColor() {
        return 0x8480FF;
    }

    @Override
    public int getGrassColor(double p_225528_1_, double p_225528_3_) {
        return 0x005F4E;
    }
}
