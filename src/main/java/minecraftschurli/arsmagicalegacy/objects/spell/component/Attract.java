package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Attract extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        doTKExtrapolated(stack, world, impactX, impactY, impactZ, caster);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        doTKExtrapolated(stack, world, target.posX, target.posY, target.posZ, caster);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 2.6f;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y, z);
//        if (effect != null) {
//            effect.addRandomOffset(1, 1, 1);
//            effect.AddParticleController(new ParticleApproachPoint(effect, x, y, z, 0.025f, 0.025f, 1, false));
//            effect.setRGBColorF(0.8f, 0.3f, 0.7f);
//            if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_INGOT))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private boolean doTKExtrapolated(ItemStack stack, World world, double impactX, double impactY, double impactZ, LivingEntity caster){
        if (caster instanceof PlayerEntity) {
            double range = 0;//EntityExtension.For(caster).getTKDistance();
            RayTraceResult mop = null;//((SpellItem)ModItems.SPELL.get()).getMovingObjectPosition(caster, world, range, false, false);
            if (mop == null) {
                impactX = caster.posX + (Math.cos(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactZ = caster.posZ + (Math.sin(Math.toRadians(caster.rotationYaw + 90)) * range);
                impactY = caster.posY + caster.getEyeHeight() + (-Math.sin(Math.toRadians(caster.rotationPitch)) * range);
            }
        }
        LivingEntity target = getClosestEntityToPointWithin(caster, world, new Vec3i(impactX, impactY, impactZ));
        if (target == null) return false;
        Vec3d movement = new Vec3d(target.getPosition().subtract(new Vec3i(impactX, impactY, impactZ))).normalize();
        if (!world.isRemote) {
            float factor = 0.75f;
            double x = -(movement.getX() * factor);
            double y = -(movement.getY() * factor);
            double z = -(movement.getZ() * factor);
            target.addVelocity(x, y, z);
            if (Math.abs(target.getMotion().getX()) > Math.abs(x * 2)) target.setMotion(x * (target.getMotion().getX() / target.getMotion().getX()), target.getMotion().getY(), target.getMotion().getZ());
            if (Math.abs(target.getMotion().getY()) > Math.abs(x * 2)) target.setMotion(target.getMotion().getX(), y * (target.getMotion().getY() / target.getMotion().getY()), target.getMotion().getZ());
            if (Math.abs(target.getMotion().getZ()) > Math.abs(z * 2)) target.setMotion(target.getMotion().getX(), target.getMotion().getY(), z * (target.getMotion().getZ() / target.getMotion().getZ()));
        }
        return true;
    }

    private LivingEntity getClosestEntityToPointWithin(LivingEntity caster, World world, Vec3i point) {
        AxisAlignedBB bb = new AxisAlignedBB(point.getX() - 16, point.getY() - 16, point.getZ() - 16, point.getX() + 16, point.getY() + 16, point.getZ() + 16);
        List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, bb);
        LivingEntity closest = null;
        for (LivingEntity e : entities){
            if (e == caster) continue;
            if (closest == null || point.distanceSq(e.getPosition()) < point.distanceSq(closest.getPosition()))
                closest = e;
        }
        return closest;
    }
}
