package minecraftschurli.arsmagicalegacy.api.rituals;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import vazkii.patchouli.api.IMultiblock;

import java.util.List;

public abstract class AbstractRitual extends ForgeRegistryEntry<AbstractRitual> {
	public abstract List<ItemStack> getReagents();
	public abstract int getReagentSearchRadius();
	public abstract IMultiblock getRitualShape();
	public abstract ItemStack getResult();
}
