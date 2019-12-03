package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.EntityUtils;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Charm extends SpellComponent /*implements IRitualInteraction*/ {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.LIFE_ESSENCE.get()))
        };
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof CreatureEntity) || /*((CreatureEntity)target).isPotionActive(PotionEffectsDefs.charme) || */EntityUtils.isSummon((CreatureEntity) target))
            return false;
        int duration = SpellUtils.getModifiedIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
//        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())){
//            duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
//            RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//        }
        if (target instanceof AnimalEntity) {
            ((AnimalEntity) target).setInLove(null);
            return true;
        }
//        if (EntityExtension.For(caster).getCanHaveMoreSummons()){
//            if (caster instanceof PlayerEntity){
//                EffectCharmed charmBuff = new EffectCharmed(duration, EffectCharmed.CHARM_TO_PLAYER);
//                charmBuff.setCharmer(caster);
//                ((CreatureEntity)target).addPotionEffect(charmBuff);
//                return true;
//            } else if (caster instanceof LivingEntity){
//                BuffEffectCharmed charmBuff = new BuffEffectCharmed(duration, BuffEffectCharmed.CHARM_TO_MONSTER);
//                charmBuff.setCharmer(caster);
//                ((CreatureEntity)target).addPotionEffect(charmBuff);
//                return true;
//            }
//        } else {
        if (caster instanceof PlayerEntity)
            caster.sendMessage(new TranslationTextComponent("chat.noMoreSummons"));//"You cannot have any more summons."
        return true;
//        }
//        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
    }


    @Override
    public float getManaCost(LivingEntity caster) {
        return 300;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 10; ++i) {
//            AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "heart", x, y, z);
//            if (particle != null){
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.05f + rand.nextFloat() * 0.1f, 1, false));
//                particle.setMaxAge(20);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.WHEAT),
                new ItemStack(Items.WHEAT_SEEDS),
                new ItemStack(Items.CARROT)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
