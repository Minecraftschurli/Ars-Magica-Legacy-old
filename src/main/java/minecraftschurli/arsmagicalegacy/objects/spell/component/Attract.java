package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class Attract extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        AxisAlignedBB bb = new AxisAlignedBB(impactX - 16, impactY - 16, impactZ - 16, impactX + 16, impactY + 16, impactZ + 16);
        List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, bb);
        LivingEntity target = null;
        Vec3i point = new Vec3i(impactX, impactY, impactZ);
        for (LivingEntity e : entities) {
            if (e == caster) continue;
            if (target == null || point.distanceSq(e.getPosition()) < point.distanceSq(target.getPosition()))
                target = e;
        }
        if (target == null) return false;
        Vec3d movement = new Vec3d(target.getPosition().subtract(new Vec3i(impactX, impactY, impactZ))).normalize();
        if (!world.isRemote) {
            double x = -movement.getX() * 0.75;
            double y = -movement.getY() * 0.75;
            double z = -movement.getZ() * 0.75;
            target.addVelocity(x, y, z);
            if (Math.abs(target.getMotion().getX()) > Math.abs(x * 2))
                target.setMotion(x, target.getMotion().getY(), target.getMotion().getZ());
            if (Math.abs(target.getMotion().getY()) > Math.abs(y * 2))
                target.setMotion(target.getMotion().getX(), y, target.getMotion().getZ());
            if (Math.abs(target.getMotion().getZ()) > Math.abs(z * 2))
                target.setMotion(target.getMotion().getX(), target.getMotion().getY(), z);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NONE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 1;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 2.6f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y, z);
//        if (effect != null) {
//            effect.addRandomOffset(1, 1, 1);
//            effect.AddParticleController(new ParticleApproachPoint(effect, x, y, z, 0.025f, 0.025f, 1, false));
//            effect.setRGBColorF(0.8f, 0.3f, 0.7f);
//            if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//        }
    }
}
