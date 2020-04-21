package minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public interface IEtheriumStorage extends INBTSerializable<CompoundNBT> {
    int getStoredAmount();
    int getMaxStoredAmount();
    boolean consume(int amount, boolean simulate);
    boolean add(int amount, boolean simulate);
    EtheriumType getType();
}
