package minecraftschurli.arsmagicalegacy.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2020-01-21
 */
public interface ISpellItem {
    RayTraceResult getMovingObjectPosition(LivingEntity caster, World world, double range, boolean includeEntities, boolean targetWater);
}
