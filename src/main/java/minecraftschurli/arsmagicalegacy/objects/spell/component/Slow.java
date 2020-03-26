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
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Slow extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtils.potionSpell(Effects.SLOWNESS, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ICE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.BUFF_POWER, SpellModifiers.DURATION);
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.SLIME_BALL)
        };
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREEN_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.SLIME_BALL))
        };
    }

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle", x, y + 1, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, -0.1f, 1, false));
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.2f, 2, false).setIgnoreYCoordinate(true).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.2f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
