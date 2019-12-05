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

public class ManaLink extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.MANA_FOCUS.get())),
//                new ItemStackSpellIngredient(new ItemStack(ModItems.MANA_BATTERY.get()))
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
//            EntityExtension.For((LivingEntity) target).updateManaLink(caster);
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMLineArc arc = (AMLineArc) ArsMagicaLegacy.proxy.particleManager.spawn(world, "textures/blocks/wipblock2.png", caster, target);
//        if (arc != null) {
//            arc.setExtendToTarget();
//            arc.setIgnoreAge(false);
//            arc.setRBGColorF(0.17f, 0.88f, 0.88f);
//        }
    }

//    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIGHTNING, Affinity.ENDER, Affinity.ARCANE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.25f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}