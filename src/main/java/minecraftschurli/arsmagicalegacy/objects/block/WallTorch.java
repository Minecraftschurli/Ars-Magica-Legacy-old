package minecraftschurli.arsmagicalegacy.objects.block;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WallTorch extends WallTorchBlock {
    public WallTorch(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Direction direction = stateIn.get(HORIZONTAL_FACING);
        double x = (double) pos.getX() + 0.5;
        double y = (double) pos.getY() + 0.7;
        double z = (double) pos.getZ() + 0.5;
        direction = direction.getOpposite();
        worldIn.addParticle(ParticleTypes.SMOKE, x + 0.27 * (double) direction.getXOffset(), y + 0.22, z + 0.27 * (double) direction.getZOffset(), 0, 0, 0);
    }
}
