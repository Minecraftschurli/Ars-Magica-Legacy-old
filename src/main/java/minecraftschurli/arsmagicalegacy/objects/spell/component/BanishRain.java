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

public class BanishRain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, blockPos);
//        if (hasMatch) {
//            RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
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
//        if (hasMatch){
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
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(Items.WATER_BUCKET),
                new ItemStack(Items.SNOW_BLOCK)
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++) {
//            AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "water_ball", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(5, 4, 5);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0f, 0.5f, 1, false));
//                particle.setMaxAge(25 + rand.nextInt(10));
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.GOLD_INGOT))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }
}
