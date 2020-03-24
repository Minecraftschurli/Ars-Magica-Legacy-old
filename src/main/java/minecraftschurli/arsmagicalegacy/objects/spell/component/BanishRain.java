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
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class BanishRain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, pos);
//        if (hasMatch) {
//            RitualShapeHelper.instance.consumeReagents(this, world, pos);
//            world.getWorldInfo().setRainTime(0);
//            world.getWorldInfo().setRaining(true);
//            return true;
//        }
        if (!world.isRaining()) return false;
        world.getWorldInfo().setRainTime(24000);
        world.getWorldInfo().setRaining(false);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
//        boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, caster.getPosition());
//        if (hasMatch) {
//            RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//            world.getWorldInfo().setRainTime(0);
//            world.getWorldInfo().setRaining(true);
//            return true;
//        }
        if (!world.isRaining()) return false;
        world.getWorldInfo().setRainTime(24000);
        world.getWorldInfo().setRaining(false);
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 750;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "water_ball", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(5, 4, 5);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0f, 0.5f, 1, false));
//                particle.setMaxAge(25 + rand.nextInt(10));
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.WATER.get());
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemTagSpellIngredient(Tags.Items.INGOTS_GOLD)
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.3f;
    }

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.hourglass;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.WATER_BUCKET),
                new ItemStack(Items.SNOW)
        };
    }

    @Override
    public float getReagentSearchRadius() {
        return 3;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
