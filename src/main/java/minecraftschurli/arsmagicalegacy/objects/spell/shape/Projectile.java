package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class Projectile extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (!world.isRemote) {
            double projectileSpeed = SpellUtil.modifyDoubleAdd(stack, caster, target, world, SpellModifiers.SPEED);
            float projectileGravity = (float) SpellUtil.modifyDoubleMul(stack, caster, target, world, SpellModifiers.GRAVITY);
            int projectileBounce = SpellUtil.modifyIntAdd(stack, caster, target, world, SpellModifiers.BOUNCE);
            SpellProjectileEntity projectile = new SpellProjectileEntity(world);
            projectile.setPosition(caster.getPosX(), caster.getEyeHeight() + caster.getPosY(), caster.getPosZ());
            projectile.setMotion(caster.getLookVec().getX() * projectileSpeed, caster.getLookVec().getY() * projectileSpeed, caster.getLookVec().getZ() * projectileSpeed);
            if (SpellUtil.hasModifier(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack)) projectile.setTargetWater();
            projectile.setGravity(projectileGravity);
            projectile.setBounces(projectileBounce);
            projectile.setNumPierces((SpellUtil.countModifiers(SpellModifiers.PIERCING, stack) * 2) * 2);
            projectile.setShooter(caster);
            projectile.setHoming(SpellUtil.hasModifier(SpellModifiers.HOMING, stack));
            projectile.setSpell(stack);
//            projectile.setIcon(AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(stack)));
            world.addEntity(projectile);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.HOMING, SpellModifiers.TARGET_NONSOLID_BLOCKS, SpellModifiers.SPEED, SpellModifiers.BOUNCE, SpellModifiers.PIERCING);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(Items.ARROW)),
                new ItemStackSpellIngredient(new ItemStack(Items.SNOWBALL))
        };
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
