package minecraftschurli.arsmagicalegacy.proxy;

import net.minecraft.entity.player.PlayerEntity;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public interface IProxy {
    default void init() {
    }

    default void preInit() {
    }

    default PlayerEntity getLocalPlayer() {
        throw new IllegalStateException("must only be run on client");
    }
}
