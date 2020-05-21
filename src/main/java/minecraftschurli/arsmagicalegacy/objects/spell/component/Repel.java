package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Repel extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target == null) return false;
        if (target == caster) {
            List<Entity> ents = world.getEntitiesWithinAABB(Entity.class, caster.getBoundingBox().expand(2, 2, 2));
            for (Entity e : ents) {
                Vec3d casterPos = new Vec3d(caster.getPosX(), caster.getPosY(), caster.getPosZ());
                Vec3d ePos = new Vec3d(e.getPosX(), e.getPosY(), e.getPosZ());
                double distance = casterPos.distanceTo(ePos) + 0.1;
                Vec3d delta = new Vec3d(ePos.getX() - casterPos.getX(), ePos.getY() - casterPos.getY(), ePos.getZ() - casterPos.getZ());
                double dX = delta.getX() / 2.5 / distance;
                double dY = delta.getY() / 2.5 / distance;
                double dZ = delta.getZ() / 2.5 / distance;
                e.setMotion(e.getPosition().getX() + dX, e.getPosition().getY() + dY, e.getPosition().getZ() + dZ);
            }
            return true;
        }
        Vec3d casterPos = new Vec3d(caster.getPosX(), caster.getPosY(), caster.getPosZ());
        Vec3d targetPos = new Vec3d(target.getPosX(), target.getPosY(), target.getPosZ());
        double distance = casterPos.distanceTo(targetPos) + 0.1;
        Vec3d delta = new Vec3d(targetPos.getX() - casterPos.getX(), targetPos.getY() - casterPos.getY(), targetPos.getZ() - casterPos.getZ());
        double dX = delta.getX() / 2.5 / distance;
        double dY = delta.getY() / 2.5 / distance;
        double dZ = delta.getZ() / 2.5 / distance;
        target.setMotion(target.getPosition().getX() + dX, target.getPosition().getY() + dY, target.getPosition().getZ() + dZ);
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NONE.get());
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 5;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 2; i++) ParticleUtil.addParticle(world, target, ModParticles.SPARKLE, colorModifier, 0, x + rand.nextInt(2) - 1, y + rand.nextInt(4) - 2, z + rand.nextInt(2) - 1);
    }
}
