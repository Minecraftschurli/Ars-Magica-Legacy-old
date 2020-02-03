package minecraftschurli.arsmagicalegacy.api.capability;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;

/**
 * @author Minecraftschurli
 * @version 2020-01-20
 */
public interface IRiftStorage extends IInventory {

    int getAccessLevel();

    void setAccessLevel(int accessLevel);

    void load(CompoundNBT nbt);

    void save(CompoundNBT nbt);
}
