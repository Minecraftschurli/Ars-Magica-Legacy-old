package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Attract extends SpellComponent {
    public Attract() {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        doTK_Extrapolated(stack, world, impactX, impactY, impactZ, caster);
        return true;
    }

    private boolean doTK_Extrapolated(ItemStack stack, World world, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (caster instanceof PlayerEntity) {
            double range = EntityExtension.For(caster).getTKDistance();
            RayTraceResult mop = ((SpellBase) ModItems.spell).getMovingObjectPosition(caster, world, range, false, false);
            if (mop == null) {
                impactX = caster.posX + (Math.cos(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactZ = caster.posZ + (Math.sin(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactY = caster.posY + caster.getEyeHeight() + (-Math.sin(Math.toRadians(caster.rotationPitch)) * range);
            }
        }
        LivingEntity target = getClosestEntityToPointWithin(caster, world, new Vec3i(impactX, impactY, impactZ), 16);
        if (target == null) return false;
        Vec3d movement = new Vec3d(target.getPosition().subtract(new Vec3i(impactX, impactY, impactZ))).normalize();
        if (!world.isRemote) {
            float factor = 0.75f;
            double x = -(movement.getX() * factor);
            double y = -(movement.getY() * factor);
            double z = -(movement.getZ() * factor);
            target.addVelocity(x, y, z);
            if (Math.abs(target.motionX) > Math.abs(x * 2)) {
                target.motionX = x * (target.motionX / target.motionX);
            }
            if (Math.abs(target.motionY) > Math.abs(y * 2)) {
                target.motionY = y * (target.motionY / target.motionY);
            }
            if (Math.abs(target.motionZ) > Math.abs(z * 2)) {
                target.motionZ = z * (target.motionZ / target.motionZ);
            }
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private LivingEntity getClosestEntityToPointWithin(LivingEntity caster, World world, Vec3i point, double radius) {
        AxisAlignedBB bb = new AxisAlignedBB(point.getX() - radius, point.getY() - radius, point.getZ() - radius, point.getX() + radius, point.getY() + radius, point.getZ() + radius);
        List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, bb);
        LivingEntity closest = null;
        for (LivingEntity e : entities) {
            if (e == caster) continue;
            if (closest == null || point.distanceSq(e.getPosition()) < point.distanceSq(closest.getPosition()))
                closest = e;
        }
        return closest;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        doTK_Extrapolated(stack, world, target.posX, target.posY, target.posZ, caster);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 2.6f;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y, z);
        if (effect != null) {
            effect.addRandomOffset(1, 1, 1);
            effect.AddParticleController(new ParticleApproachPoint(effect, x, y, z, 0.025f, 0.025f, 1, false));
            effect.setRGBColorF(0.8f, 0.3f, 0.7f);
            if (colorModifier > -1) {
                effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
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
                new ItemStack(ModItems.BLACK_RUNE.get()),
                Items.IRON_INGOT
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 1;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}