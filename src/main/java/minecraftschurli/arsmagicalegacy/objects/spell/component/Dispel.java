package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Dispel extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (EntityUtils.isSummon((LivingEntity) target)) {
            if (EntityUtils.getOwner((LivingEntity) target) == caster.getEntityId()) {
                target.attackEntityFrom(DamageSource.magic, 50000);
                return true;
            }
        }
        List<Potion> effectsToRemove = new ArrayList<>();
        int magnitudeLeft = 6;
        Iterator<PotionEffect> iter = ((LivingEntity) target).getActivePotionEffects().iterator();
        while (iter.hasNext()) {
            Potion potion = ((PotionEffect) iter.next()).getPotion();
            PotionEffect pe = ((LivingEntity) target).getActivePotionEffect(potion);
            int magnitudeCost = pe.getAmplifier();
            if (magnitudeLeft >= magnitudeCost) {
                magnitudeLeft -= magnitudeCost;
                if (pe instanceof BuffEffect && !world.isRemote) {
                    ((BuffEffect) pe).stopEffect((LivingEntity) target);
                }
                effectsToRemove.add(potion);
            }
        }
        if (effectsToRemove.size() == 0 && EntityExtension.For((LivingEntity) target).getCurrentSummons() == 0)
            return false;
        if (!world.isRemote)
            removePotionEffects((LivingEntity) target, effectsToRemove);
        //TODO:
		/*if (ExtendedProperties.For((LivingEntity)target).getNumSummons() > 0){
			if (!world.isRemote){
				Iterator it = world.loadedEntityList.iterator();
				while (it.hasNext()){
					Entity ent = (Entity)it.next();
					if (ent instanceof EntitySummonedCreature && ((EntitySummonedCreature)ent).GetOwningEntity() == target){
						ent.attackEntityFrom(DamageSource.outOfWorld, 5000);
						break;
					}
				}
			}
		}*/
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private void removePotionEffects(LivingEntity target, List<Potion> effectsToRemove) {
        for (Potion i : effectsToRemove) {
            if (i == PotionEffectsDefs.flight || i == PotionEffectsDefs.levitation) {
                if (target instanceof PlayerEntity && target.isPotionActive(PotionEffectsDefs.flight)) {
                    ((PlayerEntity) target).capabilities.isFlying = false;
                    ((PlayerEntity) target).capabilities.allowFlying = false;
                }
            }
            target.removePotionEffect(i);
        }
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 200;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 2, 1);
                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.1f + rand.nextFloat() * 0.1f, 1, false));
                if (rand.nextBoolean())
                    particle.setRGBColorF(0.7f, 0.1f, 0.7f);
                particle.setMaxAge(20);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NONE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_ARCANEASH),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_BLUE_TOPAZ),
                Items.MILK_BUCKET
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
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