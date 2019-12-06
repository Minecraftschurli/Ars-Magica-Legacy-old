package minecraftschurli.arsmagicalegacy.worldgen.biomes;

import minecraftschurli.arsmagicalegacy.objects.tree.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.surfacebuilders.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-22
 */
public class WitchwoodForestBiome extends Biome {
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
        this.addStructure(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL));
        this.addStructure(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG);
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
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(WitchwoodTree.WITCHWOOD_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
    }

    @Override
    public int getSkyColorByTemp(float p_76731_1_) {
        return 0x6699ff;
    }

    @Override
    public int getFoliageColor(BlockPos p_180625_1_) {
        return 0xdbe6e5;
    }

    @Override
    public int getGrassColor(BlockPos p_180627_1_) {
        return 0xdbe6e5;
    }
}
