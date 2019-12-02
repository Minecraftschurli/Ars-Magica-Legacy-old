package minecraftschurli.arsmagicalegacy.capabilities.magic;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class CapabilityMagic {
    @CapabilityInject(IMagicStorage.class)
    public static Capability<IMagicStorage> MAGIC = null;
}
