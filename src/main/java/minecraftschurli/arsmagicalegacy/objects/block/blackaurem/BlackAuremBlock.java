package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorBlock;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public class BlackAuremBlock extends EtheriumGeneratorBlock {
    public BlackAuremBlock() {
        super(Properties.create(Material.ROCK).notSolid(), ModTileEntities.BLACK_AUREM);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getAmbientOcclusionLightValue(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return 1.0F;
    }
}
