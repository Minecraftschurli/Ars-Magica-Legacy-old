package am2.api.spell;

import java.util.EnumSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;


public abstract class AbstractSpellPart extends IForgeRegistryEntry.Impl<AbstractSpellPart> {
	
	/**
	 * Supports :
	 *     ItemStacks
	 *     OreDict String
	 *     Essence Strings ("E:mask1|mask2" (* for any), num)
	 * @return
	 */
	public abstract Object[] getRecipe();
	
	public abstract void encodeBasicData(NBTTagCompound tag, Object[] recipe);
	
	/**
	 * What modifier affect this spell part?
	 * 
	 * @return
	 */
	public abstract EnumSet<SpellModifiers> getModifiers();

}
