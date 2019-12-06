package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

public class Projectile extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.25f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (!world.isRemote) {
            double projectileSpeed = SpellUtils.getModifiedDoubleAdd(stack, caster, target, world, SpellModifiers.SPEED);
            float projectileGravity = (float) SpellUtils.getModifiedDoubleMul(stack, caster, target, world, SpellModifiers.GRAVITY);
            int projectileBounce = SpellUtils.getModifiedIntAdd(stack, caster, target, world, SpellModifiers.BOUNCE);
            SpellProjectileEntity projectile = new SpellProjectileEntity(world);
            projectile.setPosition(caster.posX, caster.getEyeHeight() + caster.posY, caster.posZ);
            projectile.setMotion(caster.getLookVec().getX() * projectileSpeed, caster.getLookVec().getY() * projectileSpeed, caster.getLookVec().getZ() * projectileSpeed);
            if (SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack)) projectile.setTargetWater();
            projectile.setGravity(projectileGravity);
            projectile.setBounces(projectileBounce);
            projectile.setNumPierces((SpellUtils.countModifiers(SpellModifiers.PIERCING, stack) * 2) * 2);
            projectile.setShooter(caster);
            projectile.setHoming(SpellUtils.modifierIsPresent(SpellModifiers.HOMING, stack));
            projectile.setSpell(stack);
//            projectile.setIcon(AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(stack)));
            world.addEntity(projectile);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(Items.ARROW)),
                new ItemStackSpellIngredient(new ItemStack(Items.SNOWBALL)),
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.HOMING, SpellModifiers.TARGET_NONSOLID_BLOCKS, SpellModifiers.SPEED, SpellModifiers.BOUNCE, SpellModifiers.PIERCING);
    }
}
