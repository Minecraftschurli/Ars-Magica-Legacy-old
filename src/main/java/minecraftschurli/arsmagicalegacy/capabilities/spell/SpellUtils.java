package minecraftschurli.arsmagicalegacy.capabilities.spell;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public final class SpellUtils {

    public static void use(PlayerEntity player, int manaCost, int burnoutCost) {
        player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> iManaStorage.decrease(manaCost));
        player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(burnoutCost));
    }

    public static BlockPos rayTrace(World world, PlayerEntity player, double rayTraceRange) {
        Vec3d look = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d end = new Vec3d(player.posX + look.x * rayTraceRange, player.posY + player.getEyeHeight() + look.y * rayTraceRange, player.posZ + look.z * rayTraceRange);
        RayTraceContext ctx = new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        BlockRayTraceResult result = world.rayTraceBlocks(ctx);
        return result.getPos().offset(result.getFace());
    }

    public static LazyOptional<ISpell> getSpell(ItemStack stack) {
        //TODO Make Spell system
        return LazyOptional.of(TestSpell::new).cast();
    }
}
