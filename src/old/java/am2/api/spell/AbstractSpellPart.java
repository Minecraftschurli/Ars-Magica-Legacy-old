package am2.api.spell;

import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.registry.*;

import java.util.*;


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
