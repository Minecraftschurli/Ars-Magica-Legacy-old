package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public final class Projectile extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (!world.isRemote) {
            SpellProjectileEntity entity = new SpellProjectileEntity(world);
            entity.setPosition(caster.getPosX(), caster.getEyeHeight() + caster.getPosY(), caster.getPosZ());
            entity.setMotion(caster.getLookVec().getX() * SpellUtil.modifyDoubleAdd(stack, caster, target, world, SpellModifiers.SPEED), caster.getLookVec().getY() * SpellUtil.modifyDoubleAdd(stack, caster, target, world, SpellModifiers.SPEED), caster.getLookVec().getZ() * SpellUtil.modifyDoubleAdd(stack, caster, target, world, SpellModifiers.SPEED));
            if (SpellUtil.hasModifier(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack)) entity.setTargetNonSolid();
            entity.setHoming(SpellUtil.hasModifier(SpellModifiers.HOMING, stack));
            entity.setBounces(SpellUtil.modifyIntAdd(stack, caster, target, world, SpellModifiers.BOUNCE));
            entity.setGravity((float)SpellUtil.modifyDoubleMul(stack, caster, target, world, SpellModifiers.GRAVITY));
            entity.setPierces((SpellUtil.countModifiers(SpellModifiers.PIERCING, stack) * 2) * 2);
            entity.setOwner(caster);
            entity.setStack(stack);
//            entity.setIcon(AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(stack)));
            world.addEntity(entity);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.HOMING, SpellModifiers.TARGET_NONSOLID_BLOCKS, SpellModifiers.SPEED, SpellModifiers.BOUNCE, SpellModifiers.PIERCING);
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.25f;
    }
}
