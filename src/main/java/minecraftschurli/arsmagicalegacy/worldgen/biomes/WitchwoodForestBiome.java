package minecraftschurli.arsmagicalegacy.worldgen.biomes;

//import minecraftschurli.arsmagicalegacy.objects.tree.WitchwoodTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

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
        /*this.addFeature(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL));
        this.addFeature(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG);*/
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
//        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.func_225566_b_(WitchwoodTree.WITCHWOOD_TREE)Biome.(, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
    }

    @Override
    public int func_225529_c_() {
        return 0x6699ff;
    }

    @Override
    public int func_225528_a_(double p_225528_1_, double p_225528_3_) {
        return 0xdbe6e5;
    }

    @Override
    public int func_225527_a_() {
        return 0xdbe6e5;
    }
}
