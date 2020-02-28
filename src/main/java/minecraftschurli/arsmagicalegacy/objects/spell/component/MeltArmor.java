package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class MeltArmor extends SpellComponent {
    private static final String mmpsNBTTagName = "mmmpsmod";
    private static final String mmpsChargeTagName = "Current Energy";

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.SPONGE))
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

    private void doMeltArmor(LivingEntity caster, NonNullList<ItemStack> armor) {
        double mmpsCharge = -1;
        for (ItemStack stack : armor)
            if (stack != null && stack.getTag() != null) {
                CompoundNBT subCompound = (CompoundNBT) stack.getTag().get(mmpsNBTTagName);
                if (subCompound != null && subCompound.get(mmpsChargeTagName) != null)
                    mmpsCharge += subCompound.getDouble(mmpsChargeTagName);
            }
        for (ItemStack stack : armor) {
            if (stack == null) continue;
            if (!stack.hasTag() || stack.getTag().get(mmpsNBTTagName) == null)
                stack.damageItem((int) Math.ceil(stack.getItem().getMaxDamage() * 0.25f), caster, null);
            else {
                CompoundNBT subCompound = (CompoundNBT) stack.getTag().get(mmpsNBTTagName);
                double charge = stack.getTag().getDouble(mmpsChargeTagName);
                charge -= mmpsCharge * 0.75f;
                if (charge < 0) charge = 0;
                subCompound.putDouble(mmpsChargeTagName, charge);
            }
        }
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "radiant", x + 0.5, y + 0.5, z + 0.5);
//        if (particle != null) {
//            particle.AddParticleController(new ParticleHoldPosition(particle, 20, 1, false));
//            particle.setMaxAge(20);
//            particle.setParticleScale(0.3f);
//            particle.setRGBColorF(0.7f, 0.4f, 0.2f);
//            particle.SetParticleAlpha(0.1f);
//            if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.FIRE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0;
//    }
//
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
