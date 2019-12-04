package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.entity.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
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
                new ItemStack(ModItems.FIRE_ESSENCE.get()),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_ARCANEASH),
                Blocks.NETHERRACK,
                new ItemStack(ModItems.FIRE_ESSENCE.get()),
                Items.LAVA_BUCKET
        };
    }

    private boolean spawnFireRain(ItemStack stack, World world, LivingEntity caster, Entity target, double x, double y, double z) {
        List<EntitySpellEffect> zones = world.getEntitiesWithinAABB(EntitySpellEffect.class, new AxisAlignedBB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
        for (EntitySpellEffect zone : zones) {
            if (zone.isRainOfFire())
                return false;
        }
        if (!world.isRemote) {
            int radius = SpellUtils.getModifiedIntAdd(2, stack, caster, target, world, SpellModifiers.RADIUS) / 2 + 1;
            double damage = SpellUtils.getModifiedDoubleMul(1, stack, caster, target, world, SpellModifiers.DAMAGE);
            int duration = SpellUtils.getModifiedIntMul(100, stack, caster, target, world, SpellModifiers.DURATION);
            EntitySpellEffect fire = new EntitySpellEffect(world);
            fire.setPosition(x, y, z);
            fire.setRainOfFire(false);
            fire.setRadius(radius);
            fire.setDamageBonus((float) damage);
            fire.setTicksToExist(duration);
            fire.SetCasterAndStack(caster, stack);
            world.addEntity(fire);
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
        return spawnFireRain(stack, world, caster, target, target.posX, target.posY, target.posZ);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 3000;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.FIRE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.1f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}