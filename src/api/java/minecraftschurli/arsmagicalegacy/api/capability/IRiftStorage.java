package minecraftschurli.arsmagicalegacy.api.capability;

import net.minecraft.inventory.*;
import net.minecraft.nbt.*;

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
