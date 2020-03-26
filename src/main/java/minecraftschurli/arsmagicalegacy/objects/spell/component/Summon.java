package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.util.EntityUtils;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Summon extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        CreatureEntity entity;
        try {
            entity = new SkeletonEntity(EntityType.SKELETON, world);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        entity.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.BOW));
        entity.setPosition(impactX, impactY, impactZ);
        world.addEntity(entity);
        if (caster instanceof PlayerEntity) EntityUtils.makeSummonPlayerFaction(entity, (PlayerEntity) caster, false);
        else EntityUtils.makeSummonMonsterFaction(entity, false);
        EntityUtils.setOwner(entity, caster);
        int duration = SpellUtils.getModifiedIntMul(4800, stack, caster, caster, world, SpellModifiers.DURATION);
        EntityUtils.setSummonDuration(entity, duration);
        SpellUtils.applyStageToEntity(stack, caster, world, entity, false);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && EntityUtils.isSummon((LivingEntity) target)) return false;
        return SpellUtils.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.LIFE.get(), ModSpellParts.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_CHIMERITE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURIFIED_VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.CERUBLOSSOM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MONSTER_FOCUS.get())),
                new EtheriumSpellIngredient(1500, EtheriumType.DARK)
        };
    }
}
