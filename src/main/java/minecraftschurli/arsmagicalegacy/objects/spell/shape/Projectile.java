package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
//import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Projectile extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        if (!world.isRemote){
//            SpellProjectileEntity projectile = new SpellProjectileEntity(world);
//            projectile.setShootingEntity(caster);
//            projectile.setBounces(SpellUtils.getModifiedIntAdd(SpellModifier.Type.BOUNCE, stack, caster, target, world, 0));
//            projectile.setEffectStack(stack);
//            if (SpellUtils.modifierIsPresent(SpellModifier.Type.TARGET_NONSOLID_BLOCKS, stack, 0)) projectile.setTargetWater();
//            projectile.setGravity(SpellUtils.getModifiedDoubleAdd(SpellModifier.Type.GRAVITY, stack, caster, target, world, 0));
//            projectile.setNumPierces(SpellUtils.getModifiedIntAdd(0, stack, caster, target, world, 0, SpellModifier.Type.PIERCING));
//            projectile.setHoming(SpellUtils.getModifiedIntAdd(SpellModifier.Type.HOMING, stack, caster, target, world, 0));
//            world.spawnEntityInWorld(projectile);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack) {
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
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ARROW)),
                new ItemStackSpellIngredient(new ItemStack(Items.SNOWBALL))
        };
    }
}
