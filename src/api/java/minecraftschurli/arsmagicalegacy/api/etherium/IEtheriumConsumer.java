package minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.util.math.BlockPos;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public interface IEtheriumConsumer {
    BlockPos getEteriumSource();

    void invalidateEtheriumSource();

    boolean shouldConsume();

    void setEtheriumSource(BlockPos readPos);
}
