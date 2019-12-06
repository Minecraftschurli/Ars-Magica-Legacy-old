package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

@SuppressWarnings("deprecated")
public class Recall extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (caster.isPotionActive(ModEffects.ASTRAL_DISTORTION.get()) || ((LivingEntity) target).isPotionActive(ModEffects.ASTRAL_DISTORTION.get()))
            if (caster instanceof PlayerEntity) {
                caster.sendMessage(new TranslationTextComponent("minecraftschurli.arsmagicalegacy.tooltip.cantTeleport"));
                return false;
            }
//        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
//            ItemStack[] ritualRunes = RitualShapeHelper.instance.checkForRitual(this, world, target.getPosition());
//            if (ritualRunes != null) return handleRitualReagents(ritualRunes, world, target.getPosition(), caster, target);
//        }
        if (caster.dimension.getId() == -512) {
            if (caster instanceof PlayerEntity && !world.isRemote)
                caster.sendMessage(new TranslationTextComponent("minecraftschurli.arsmagicalegacy.tooltip.cantTeleport"));
            return false;
        }
        if (!world.isRemote) target.setPositionAndUpdate(caster.posX, caster.posY, caster.posZ);
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 500;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "arcane", x, y - 1, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0, 1);
//                particle.AddParticleController(new ParticleExpandingCollapsingRingAtPoint(particle, x, y - 1, z, 0.1, 3, 0.3, 1, false).setCollapseOnce());
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.2f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ARCANE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ORANGE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.COMPASS)),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
                new ItemStackSpellIngredient(new ItemStack(Items.MAP))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.1f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.RUNE.get()),
                new ItemStack(ModItems.RUNE.get()),
                new ItemStack(ModItems.RUNE.get())
        };
    }

    //    @Override
//    public int getReagentSearchRadius() {
//        return RitualShapeHelper.instance.ringedCross.getWidth();
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
