package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.packet.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Repel extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target == null)
            return false;
        if (target == caster) {
            LivingEntity source = caster;
            if (target instanceof LivingEntity)
                source = (LivingEntity) target;
            List<Entity> ents = world.getEntitiesWithinAABB(Entity.class, source.getEntityBoundingBox().expand(2, 2, 2));
            for (Entity e : ents) {
                performRepel(world, caster, e);
            }
            return true;
        }
        performRepel(world, caster, target);
        return true;
    }

    private void performRepel(World world, LivingEntity caster, Entity target) {
        Vec3d casterPos = new Vec3d(caster.posX, caster.posY, caster.posZ);
        Vec3d targetPos = new Vec3d(target.posX, target.posY, target.posZ);
        double distance = casterPos.distanceTo(targetPos) + 0.1D;
        Vec3d delta = new Vec3d(targetPos.getX() - casterPos.getX(), targetPos.getY() - casterPos.getY(), targetPos.getZ() - casterPos.getZ());
        double dX = delta.getX() / 2.5D / distance;
        double dY = delta.getY() / 2.5D / distance;
        double dZ = delta.getZ() / 2.5D / distance;
        if (target instanceof PlayerEntity) {
            AMNetHandler.INSTANCE.sendVelocityAddPacket(world, (PlayerEntity) target, dX, dY, dZ);
        }
        target.motionX += dX;
        target.motionY += dY;
        target.motionZ += dZ;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 5.0f;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < ArsMagica2.config.getGFXLevel() * 2; i++) {
            AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
            if (effect != null) {
                effect.addRandomOffset(1, 2, 1);
                //double dx = caster.posX - target.posX;
                //double dz = caster.posZ - target.posZ;
                //double angle = Math.toDegrees(Math.atan2(-dz, -dx));
                //effect.AddParticleController(new ParticleMoveOnHeading(effect, angle, 0, 0.1 + rand.nextDouble() * 0.5, 1, false));
                effect.AddParticleController(new ParticleFleePoint(effect, new Vec3d(caster).add(new Vec3d(0, caster.getEyeHeight(), 0)).toVec3D(), 0.075f, 3f, 1, true));
                effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f));
                effect.setMaxAge(20);
                if (colorModifier > -1) {
                    effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NONE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.WHITE_RUNE.get()),
                Items.WATER_BUCKET
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}