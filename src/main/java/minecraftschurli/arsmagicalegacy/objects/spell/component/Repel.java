package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        return Sets.newHashSet(ModSpellParts.NONE.get());
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
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < ArsMagicaLegacy.config.getGFXLevel() * 2; i++) {
//            AMParticle effect = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle", x, y, z);
//            if (effect != null) {
//                effect.addRandomOffset(1, 2, 1);
//                effect.AddParticleController(new ParticleFleePoint(effect, caster.getPositionVec().add(new Vec3d(0, caster.getEyeHeight(), 0)), 0.075f, 3f, 1, true));
//                effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f));
//                effect.setMaxAge(20);
//                if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
