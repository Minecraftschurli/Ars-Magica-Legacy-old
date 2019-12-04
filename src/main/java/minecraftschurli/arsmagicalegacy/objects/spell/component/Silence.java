package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
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

public class Silence extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                Blocks.WOOL,
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_ARCANEASH),
                Blocks.JUKEBOX,
                Blocks.WOOL
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && target.isNonBoss()) {
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new BuffEffectSilence(PotionEffectsDefs.default_buff_duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 800;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM)
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z,
                               LivingEntity caster, Entity target, Random rand,
                               int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.WATER, Affinity.ENDER);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}