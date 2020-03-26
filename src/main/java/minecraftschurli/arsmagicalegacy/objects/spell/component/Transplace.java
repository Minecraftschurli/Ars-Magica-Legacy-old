package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Transplace extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!world.isRemote && target != null && target.isAlive()) {
            double tPosX = target.getPosX();
            double tPosY = target.getPosY();
            double tPosZ = target.getPosZ();
            double cPosX = caster.getPosX();
            double cPosY = caster.getPosY();
            double cPosZ = caster.getPosZ();
            caster.setPositionAndUpdate(tPosX, tPosY, tPosZ);
            if (target instanceof LivingEntity) target.setPositionAndUpdate(cPosX, cPosY, cPosZ);
            else target.setPosition(cPosX, cPosY, cPosZ);
        }
//        if (target instanceof LivingEntity) ((LivingEntity) target).lookAt(caster, 180, 180);
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 100;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.PURIFIED_VINTEUM.get()),
                new ItemStack(ModItems.PLAYER_FOCUS.get()),
                new ItemStack(ModItems.MAGE_HELMET.get()),
                new ItemStack(ModItems.MAGE_CHESTPLATE.get()),
                new ItemStack(ModItems.MAGE_LEGGINGS.get()),
                new ItemStack(ModItems.MAGE_BOOTS.get())
        };
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.COMPASS)),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
        };
    }

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", caster.getPosX(), caster.getPosY() + caster.getEyeHeight(), caster.getPosZ());
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleArcToPoint(particle, 1, target.getPosX(), target.getPosY() + target.getEyeHeight(), target.getPosZ(), false).SetSpeed(0.05f).generateControlPoints());
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.2f);
//                particle.setRGBColorF(1, 0, 0);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
//        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", target.getPosX(), target.getPosY() + target.getEyeHeight(), target.getPosZ());
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleArcToPoint(particle, 1, caster.getPosX(), caster.getPosY() + caster.getEyeHeight(), caster.getPosZ(), false).SetSpeed(0.05f).generateControlPoints());
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.2f);
//                particle.setRGBColorF(0, 0, 1);
//                if (colorModifier > -1) particle.setRGBColorF((0xFF - ((colorModifier >> 16) & 0xFF)) / 255, (0xFF - ((colorModifier >> 8) & 0xFF)) / 255, (0xFF - (colorModifier & 0xFF)) / 255);
//            }
//        }
    }
}
