package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class MeltArmor extends SpellComponent {
    private static final String mmpsNBTTagName = "mmmpsmod";
    private static final String mmpsChargeTagName = "Current Energy";

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(Blocks.SPONGE)
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof PlayerEntity && !world.isRemote) {
            doMeltArmor(caster, ((PlayerEntity) target).inventory.armorInventory);
            return true;
        }
        return false;
    }

    private void doMeltArmor(LivingEntity caster, ItemStack[] armor) {
        double mmpsCharge = getMMPSCharge(armor);
        for (ItemStack stack : armor) {
            if (stack == null) continue;
            if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey(mmpsNBTTagName)) {
                stack.damageItem((int) Math.ceil(stack.getItem().getMaxDamage() * 0.25f), caster);
            } else {
                CompoundNBT subCompound = (CompoundNBT) stack.getTagCompound().getTag(mmpsNBTTagName);
                double charge = stack.getTagCompound().getDouble(mmpsChargeTagName);
                charge -= mmpsCharge * 0.75f;
                if (charge < 0) charge = 0;
                subCompound.setDouble(mmpsChargeTagName, charge);
            }
        }
    }

    private double getMMPSCharge(ItemStack[] armor) {
        double total = -1;
        for (ItemStack stack : armor) {
            if (stack != null && stack.hasTagCompound()) {
                CompoundNBT subCompound = (CompoundNBT) stack.getTagCompound().getTag(mmpsNBTTagName);
                if (subCompound != null && subCompound.hasKey(mmpsChargeTagName)) {
                    total += subCompound.getDouble(mmpsChargeTagName);
                }
            }
        }
        return total;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "radiant", x + 0.5, y + 0.5, z + 0.5);
        if (particle != null) {
            particle.AddParticleController(new ParticleHoldPosition(particle, 20, 1, false));
            particle.setMaxAge(20);
            particle.setParticleScale(0.3f);
            particle.setRGBColorF(0.7f, 0.4f, 0.2f);
            particle.SetParticleAlpha(0.1f);
            if (colorModifier > -1) {
                particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.FIRE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}