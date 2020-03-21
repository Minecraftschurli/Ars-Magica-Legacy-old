package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.util.EntityUtils;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

@SuppressWarnings("deprecated")
public class Summon extends SpellComponent {
    public LivingEntity summonCreature(ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z) {
        LivingEntity entity = null;
        try {
            entity = new SkeletonEntity(EntityType.SKELETON, world);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        entity.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.BOW));
        entity.setPosition(x, y, z);
        world.addEntity(entity);
        if (caster instanceof PlayerEntity)
            EntityUtils.makeSummonPlayerFaction((CreatureEntity) entity, (PlayerEntity) caster, false);
        else EntityUtils.makeSummonMonsterFaction((CreatureEntity) entity, false);
        EntityUtils.setOwner(entity, caster);
        int duration = SpellUtils.getModifiedIntMul(4800, stack, caster, target, world, SpellModifiers.DURATION);
        EntityUtils.setSummonDuration(entity, duration);
        SpellUtils.applyStageToEntity(stack, caster, world, entity, false);
        return entity;
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

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isRemote) {
//            if (EntityExtension.For(caster).getCanHaveMoreSummons()) return summonCreature(stack, caster, caster, world, impactX, impactY, impactZ) != null;
//            else if (caster instanceof PlayerEntity) caster.sendMessage(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".chat.noSummons"));
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && EntityUtils.isSummon((LivingEntity) target)) return false;
        if (!world.isRemote) {
//            if (EntityExtension.For(caster).getCanHaveMoreSummons()) return summonCreature(stack, caster, caster, world, target.posX, target.posY, target.posZ) != null;
//            else if (caster instanceof PlayerEntity) caster.sendMessage(new TranslationTextComponent("ArsMagicaLegacy.MODID + ".chat.noSummons"));
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[0];
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {

    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ENDER, Affinity.LIFE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
