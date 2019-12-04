package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.enchantments.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class MagicDamage extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        float baseDamage = 6;
        double damage = SpellUtils.getModifiedDoubleAdd(baseDamage, stack, caster, target, world, SpellModifiers.DAMAGE);
        float mod = 0.0f;
        if (target instanceof PlayerEntity) {
            for (ItemStack item : ((PlayerEntity) target).inventory.armorInventory) {
                if (item == null)
                    continue;
                if (EnchantmentHelper.getEnchantmentLevel(AMEnchantments.magicResist, item) > 0)
                    mod = mod + (0.04f * EnchantmentHelper.getEnchantmentLevel(AMEnchantments.magicResist, item));
            }
        }
        damage = damage - (damage * mod);
        return SpellUtils.attackTargetSpecial(stack, target, DamageSources.causeMagicDamage(caster), SpellUtils.modifyDamage(caster, (float) damage));
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 0.5, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2, rand.nextDouble() * 0.2 - 0.1);
                particle.setAffectedByGravity();
                particle.setDontRequireControllers();
                particle.setMaxAge(5);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ARCANE, Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                new ItemStack(Items.DYE, 1, 4),
                Items.BOOK,
                Items.STONE_SWORD
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        if (affinity == Affinity.ENDER)
            return 0.005f;
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }
}