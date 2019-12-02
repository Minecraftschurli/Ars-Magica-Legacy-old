package am2.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * This is just here for reference - you shouldn't have any need to inherit from this item.  To add new parts to spells, inherit from ISpellShape, ISpellModifier, or ISpellComponent.
 *
 * @author Mithion
 */
public abstract class ItemSpellBase extends ItemArsMagica{
	public ItemSpellBase(){
		super();
	}

	/**
	 * Specific version of getMovingObjectPosition that takes into account the targeting types that I need
	 *
	 * @param caster         The caster of the spell
	 * @param world          The world the spell is being cast in
	 * @param range          The range to raycast
	 * @param targetEntities Stop at collision with entities
	 * @param targetWater    Stop at collision with any non-solid block
	 * @return A MovingObjectPosition instance if a collision was found, null otherwise
	 */
	public abstract RayTraceResult getMovingObjectPosition(EntityLivingBase caster, World world, double range, boolean targetEntities, boolean targetWater);
}
