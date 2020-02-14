package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class FireRain extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.FIRE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.FIRE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.LAVA_BUCKET)),
                new ItemStackSpellIngredient(new ItemStack(Items.NETHERRACK))
        };
    }

    private boolean spawnFireRain(ItemStack stack, World world, LivingEntity caster, Entity target, double x, double y, double z) {
//        List<EntitySpellEffect> zones = world.getEntitiesWithinAABB(EntitySpellEffect.class, new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
//        for (EntitySpellEffect zone : zones) if (zone.isRainOfFire()) return false;
        if (!world.isRemote) {
//            EntitySpellEffect fire = new EntitySpellEffect(world);
//            fire.setPosition(x, y, z);
//            fire.setRainOfFire(false);
//            fire.setRadius(SpellUtils.getModifiedIntAdd(2, stack, caster, target, world, SpellModifiers.RADIUS) / 2 + 1);
//            fire.setDamageBonus(SpellUtils.getModifiedDoubleMul(1, stack, caster, target, world, SpellModifiers.DAMAGE));
//            fire.setTicksToExist(SpellUtils.getModifiedIntMul(100, stack, caster, target, world, SpellModifiers.DURATION));
//            fire.SetCasterAndStack(caster, stack);
//            world.addEntity(fire);
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.COLOR);
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return spawnFireRain(stack, world, caster, caster, impactX, impactY, impactZ);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return spawnFireRain(stack, world, caster, target, target.getPosX(), target.getPosY(), target.getPosZ());
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 3000;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.FIRE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.1f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
