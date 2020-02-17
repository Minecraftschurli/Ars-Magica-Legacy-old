package minecraftschurli.arsmagicalegacy.api;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-21
 */
public interface ISpellItem {
    RayTraceResult getMovingObjectPosition(LivingEntity caster, World world, double range, boolean includeEntities, boolean targetWater);
}
