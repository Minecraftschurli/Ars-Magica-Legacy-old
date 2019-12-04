package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Fury extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                Items.FISH,
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_SUNSTONE)
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = SpellUtils.getModifiedIntMul(PotionEffectsDefs.default_buff_duration, stack, caster, target, world, SpellModifiers.DURATION);
            //duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);
            if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
                duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
                RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
            }
            if (!world.isRemote) {
                ((LivingEntity) target).addPotionEffect(new BuffEffectFury(duration, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 261;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5 * ArsMagica2.config.getGFXLevel(); ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "pulse", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.setRGBColorF(1, 0, 0);
                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.15f, 1, false).SetTargetDistance(world.rand.nextDouble() + 1f).setIgnoreYCoordinate(true));
                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
                particle.setMaxAge(10);
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.FIRE, Affinity.LIGHTNING);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.hourglass;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.SWIFTNESS),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRENGTH)
        };
    }

    @Override
    public int getReagentSearchRadius() {
        return 3;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getResult() {
        return null;
    }
}