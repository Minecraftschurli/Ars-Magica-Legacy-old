package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.IContingencyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-03-31
 */
public class ContingencyStorage implements IContingencyStorage {
    private ResourceLocation contingency;
    private ItemStack spell;

    @Override
    public ResourceLocation getContingency() {
        return contingency;
    }

    @Override
    public void setContingency(ResourceLocation contingency) {
        this.contingency = contingency;
    }

    @Override
    public ItemStack getSpell() {
        return spell;
    }

    @Override
    public void setSpell(ItemStack spell) {
        this.spell = spell;
    }
}
