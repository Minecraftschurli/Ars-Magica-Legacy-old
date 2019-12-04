package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Telekinesis extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return doTK_Extrapolated(stack, world, impactX, impactY, impactZ, caster);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return doTK_Extrapolated(stack, world, target.posX, target.posY, target.posZ, caster);
    }

    private boolean doTK_Extrapolated(ItemStack stack, World world, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (caster instanceof PlayerEntity) {
            double range = ((EntityExtension) EntityExtension.For(caster)).getTKDistance();
            RayTraceResult mop = ModItems.spell.getMovingObjectPosition(caster, world, range, false, false);
            if (mop == null) {
                impactX = caster.posX + (Math.cos(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactZ = caster.posZ + (Math.sin(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactY = caster.posY + caster.getEyeHeight() + (-Math.sin(Math.toRadians(caster.rotationPitch)) * range);
            }
        }
        double distance = 16;
        int hDist = 3;
        List<Entity> entities = new ArrayList<Entity>();
        entities.addAll(world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(impactX - distance, impactY - hDist, impactZ - distance, impactX + distance, impactY + hDist, impactZ + distance)));
        entities.addAll(world.getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(impactX - distance, impactY - hDist, impactZ - distance, impactX + distance, impactY + hDist, impactZ + distance)));
        for (Entity e : entities) {
            if (e.ticksExisted < 20) {
                continue;
            }
            Vec3d movement = new Vec3d(e.posX, e.posY, e.posZ).subtract(new Vec3d(impactX, impactY, impactZ)).normalize();
            if (!world.isRemote) {
                float factor = 0.15f;
                if (movement.getY() > 0) movement = new Vec3d(movement.getX(), 0, movement.getZ());
                double x = -(movement.getX() * factor);
                double y = -(movement.getY() * factor);
                double z = -(movement.getZ() * factor);
                e.addVelocity(x, y, z);
                if (Math.abs(e.motionX) > Math.abs(x * 2)) {
                    e.motionX = x * (e.motionX / e.motionX);
                }
                if (Math.abs(e.motionY) > Math.abs(y * 2)) {
                    e.motionY = y * (e.motionY / e.motionY);
                }
                if (Math.abs(e.motionZ) > Math.abs(z * 2)) {
                    e.motionZ = z * (e.motionZ / e.motionZ);
                }
            }
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 6;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        if (caster instanceof PlayerEntity) {
            double range = EntityExtension.For(caster).getTKDistance();
            RayTraceResult mop = ModItems.spell.getMovingObjectPosition(caster, world, range, false, false);
            if (mop == null) {
                x = caster.posX + (Math.cos(Math.toRadians(caster.rotationYaw + 90)) * range);
                z = caster.posZ + (Math.sin(Math.toRadians(caster.rotationYaw + 90)) * range);
                y = caster.posY + caster.getEyeHeight() + (-Math.sin(Math.toRadians(caster.rotationPitch)) * range);
            }
        }
        AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x - 0.5 + rand.nextDouble(), y - 0.5 + rand.nextDouble(), z - 0.5 + rand.nextDouble());
        if (effect != null) {
            effect.AddParticleController(new ParticleApproachPoint(effect, x, y, z, 0.025f, 0.025f, 1, false));
            effect.setRGBColorF(0.8f, 0.3f, 0.7f);
            if (colorModifier > -1) {
                effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ARCANE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                Blocks.STICKY_PISTON,
                Blocks.CHEST
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}