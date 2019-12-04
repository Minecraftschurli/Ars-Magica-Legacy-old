package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class BanishRain extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, pos);
        if (hasMatch) {
            RitualShapeHelper.instance.consumeReagents(this, world, pos);
            world.getWorldInfo().setRainTime(0);
            world.getWorldInfo().setRaining(true);
            return true;
        }
        if (!world.isRaining()) return false;
        world.getWorldInfo().setRainTime(24000);
        world.getWorldInfo().setRaining(false);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, caster.getPosition());
        if (hasMatch) {
            RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
            world.getWorldInfo().setRainTime(0);
            world.getWorldInfo().setRaining(true);
            return true;
        }
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
    public ItemStack[] reagents(LivingEntity caster) {
        int waterMeta = 0;
        for (Affinity aff : ArsMagicaAPI.getAffinityRegistry().getValues()) {
            if (aff.equals(Affinity.NONE))
                continue;
            if (aff.equals(Affinity.WATER))
                break;
            waterMeta++;
        }
        return new ItemStack[]{new ItemStack(ModItems.essence, 1, waterMeta)};
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "water_ball", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(5, 4, 5);
                particle.AddParticleController(new ParticleFloatUpward(particle, 0f, 0.5f, 1, false));
                particle.setMaxAge(25 + rand.nextInt(10));
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.WATER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLUE_RUNE.get()),
                Items.GOLD_INGOT
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.3f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.hourglass;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(Items.WATER_BUCKET),
                new ItemStack(Blocks.SNOW)
        };
    }

    @Override
    public int getReagentSearchRadius() {
        return 3;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getResult() {
        return null;
    }
}