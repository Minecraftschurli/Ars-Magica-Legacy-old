package minecraftschurli.arsmagicalegacy.capabilities.mana;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class ManaStorage implements IManaStorage {

    int mana;

    @Override
    public int getMana() {
        return mana;
    }
}
