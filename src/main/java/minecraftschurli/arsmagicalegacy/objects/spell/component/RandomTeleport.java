package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;

import java.util.*;

public class RandomTeleport extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        Vec3d rLoc = getRandomTeleportLocation(world, stack, caster, target);
        return teleportTo(rLoc.getX(), rLoc.getY(), rLoc.getZ(), target);
    }

    private Vec3d getRandomTeleportLocation(World world, ItemStack stack, LivingEntity caster, Entity target) {
        Vec3d origin = new Vec3d(target.posX, target.posY, target.posZ);
        float maxDist = 9;
        maxDist = (float) SpellUtils.getModifiedDoubleMul(maxDist, stack, caster, target, world, SpellModifiers.RANGE);
        origin = origin.add(new Vec3d((world.rand.nextDouble() - 0.5) * maxDist, (world.rand.nextDouble() - 0.5) * maxDist, (world.rand.nextDouble() - 0.5) * maxDist));
        return origin;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }

    protected boolean teleportTo(double par1, double par3, double par5, Entity target) {
        if (target instanceof LivingEntity) {
            EnderTeleportEvent event = new EnderTeleportEvent((LivingEntity) target, par1, par3, par5, 0);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }
            par1 = event.getTargetX();
            par3 = event.getTargetY();
            par5 = event.getTargetZ();
        }
        double d3 = target.posX;
        double d4 = target.posY;
        double d5 = target.posZ;
        target.posX = par1;
        target.posY = par3;
        target.posZ = par5;
        boolean locationValid = false;
        BlockPos pos = target.getPosition();
        Block l;
        if (target.worldObj.getBlockState(pos) != null) {
            boolean targetBlockIsSolid = false;
            while (!targetBlockIsSolid && pos.getY() > 0) {
                l = target.worldObj.getBlockState(pos.down()).getBlock();
                if (l != Blocks.AIR && l.isPassable(target.worldObj, pos)) {
                    targetBlockIsSolid = true;
                } else {
                    --target.posY;
                    pos = pos.down();
                }
            }
            if (targetBlockIsSolid) {
                target.setPosition(target.posX, target.posY, target.posZ);
                if (target.worldObj.getCollisionBoxes(target, target.getEntityBoundingBox()).isEmpty()) {
                    locationValid = true;
                }
            }
        }
        if (!locationValid) {
            target.setPosition(d3, d4, d5);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 52.5f;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        world.spawnParticle(EnumParticleTypes.PORTAL, target.posX + (rand.nextDouble() - 0.5D) * target.width, target.posY + rand.nextDouble() * target.height - 0.25D, target.posZ + (rand.nextDouble() - 0.5D) * target.width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLACK_RUNE.get()),
                Items.ENDER_PEARL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}