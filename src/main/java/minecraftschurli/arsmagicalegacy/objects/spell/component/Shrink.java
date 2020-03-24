package minecraftschurli.arsmagicalegacy.objects.spell.component;

import java.util.EnumSet;
import java.util.Random;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class Shrink extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
//                new ItemStackSpellIngredient(new ItemStack(ModItems.MANA_CAKE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.BONE)),
                new ItemStackSpellIngredient(new ItemStack(Items.BROWN_MUSHROOM)),
                new ItemTagSpellIngredient(Tags.Items.NUGGETS_GOLD),
                new ItemStackSpellIngredient(new ItemStack(Items.SPIDER_EYE)),
                new ItemStackSpellIngredient(new ItemStack(Items.STONE_BUTTON))
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && target.isNonBoss()) {
            int duration = SpellUtils.getModifiedIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
//            if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//                duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
//                RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//            }
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new EffectInstance(ModEffects.SHRINK.get(), duration, 0));
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER, SpellModifiers.DURATION);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 120;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NONE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.05f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.BLAZE_ROD)
        };
    }

    //    @Override
//    public int getReagentSearchRadius() {
//        return 3;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
