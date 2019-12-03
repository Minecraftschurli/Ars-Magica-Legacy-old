package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Blizzard extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        List<EntitySpellEffect> zones = world.getEntitiesWithinAABB(EntitySpellEffect.class, new AxisAlignedBB(impactX - 10, impactY - 10, impactZ - 10, impactX + 10, impactY + 10, impactZ + 10));
//        for (EntitySpellEffect zone : zones) if (zone.isBlizzard()) return false;
        if (!world.isRemote){
            int radius = SpellUtils.getModifiedIntAdd(2, stack, caster, caster, world, SpellModifiers.RADIUS);
            double damage = SpellUtils.getModifiedDoubleMul(1, stack, caster, caster, world, SpellModifiers.DAMAGE);
            int duration = SpellUtils.getModifiedIntMul(100, stack, caster, caster, world, SpellModifiers.DURATION);
//            EntitySpellEffect blizzard = new EntitySpellEffect(world);
//            blizzard.setPosition(x, y, z);
//            blizzard.setBlizzard();
//            blizzard.setRadius(radius);
//            blizzard.setTicksToExist(duration);
//            blizzard.setDamageBonus((float)damage);
//            blizzard.SetCasterAndStack(caster, stack);
//            world.addEntity(blizzard);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
//        List<EntitySpellEffect> zones = world.getEntitiesWithinAABB(EntitySpellEffect.class, new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
//        for (EntitySpellEffect zone : zones) if (zone.isBlizzard()) return false;
        if (!world.isRemote){
            int radius = SpellUtils.getModifiedIntAdd(2, stack, caster, target, world, SpellModifiers.RADIUS);
            double damage = SpellUtils.getModifiedDoubleMul(1, stack, caster, target, world, SpellModifiers.DAMAGE);
            int duration = SpellUtils.getModifiedIntMul(100, stack, caster, target, world, SpellModifiers.DURATION);
//            EntitySpellEffect blizzard = new EntitySpellEffect(world);
//            blizzard.setPosition(x, y, z);
//            blizzard.setBlizzard();
//            blizzard.setRadius(radius);
//            blizzard.setTicksToExist(duration);
//            blizzard.setDamageBonus((float)damage);
//            blizzard.SetCasterAndStack(caster, stack);
//            world.addEntity(blizzard);
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 1200;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {

    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ICE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ICE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TOPAZ.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ICE))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.COLOR);
    }
}
