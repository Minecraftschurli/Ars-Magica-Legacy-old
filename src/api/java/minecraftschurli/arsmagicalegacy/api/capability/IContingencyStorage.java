package minecraftschurli.arsmagicalegacy.api.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-03-31
 */
public interface IContingencyStorage {
    ResourceLocation getContingency();

    void setContingency(ResourceLocation contingency);

    ItemStack getSpell();

    void setSpell(ItemStack spell);

    default void setFrom(IContingencyStorage contingencyStorage) {
        setContingency(contingencyStorage.getContingency());
    }
}
