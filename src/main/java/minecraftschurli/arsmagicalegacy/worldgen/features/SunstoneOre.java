package minecraftschurli.arsmagicalegacy.worldgen.features;

import net.minecraft.fluid.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

import javax.annotation.*;
import java.util.*;

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
