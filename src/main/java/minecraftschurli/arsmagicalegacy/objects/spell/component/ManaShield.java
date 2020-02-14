package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.capability.*;
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

public class ManaShield extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.MANA_FOCUS.get())),
//                new ItemStackSpellIngredient(new ItemStack(ModItems.BATTLEMAGE_CHESTPLATE.get())),
//                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGE_CHESTPLATE.get()))
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        float consumed = CapabilityHelper.getMana(caster);
        CapabilityHelper.decreaseMana(caster, consumed);
//        EntityExtension.For((LivingEntity) target).addMagicShielding((consumed / 250) * SpellUtils.getModifiedIntAdd(1, stack, caster, target, world, SpellModifiers.BUFF_POWER));
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public float getBurnout(LivingEntity caster) {
        return 0;
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
//        return Sets.newHashSet(Affinity.ARCANE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
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
