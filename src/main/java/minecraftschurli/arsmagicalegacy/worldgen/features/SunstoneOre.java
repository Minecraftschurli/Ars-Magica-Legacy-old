package minecraftschurli.arsmagicalegacy.worldgen.features;

import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

/**
 * @author Minecraftschurli
 * @version 2020-01-18
 */
public class SunstoneOre extends Feature<OreFeatureConfig> {
    public SunstoneOre() {
        super(OreFeatureConfig::deserialize);
    }

    @Override
    public boolean place(@Nonnull IWorld worldIn, @Nonnull ChunkGenerator<? extends GenerationSettings> generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull OreFeatureConfig config) {
        if (rand.nextInt(10) < 8)
            return false;
        for (Direction direction : Direction.values()) {
            IFluidState fluidState = worldIn.getFluidState(pos.offset(direction));
            if (fluidState.isSource() && fluidState.getFluid().isEquivalentTo(Fluids.LAVA)) {
                worldIn.setBlockState(pos, config.state, 0);
                return true;
            }
        }
        return false;
    }
}
