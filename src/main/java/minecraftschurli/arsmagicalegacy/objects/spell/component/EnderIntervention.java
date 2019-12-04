package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class EnderIntervention extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (world.isRemote || !(target instanceof LivingEntity)) return true;
        if (((LivingEntity) target).isPotionActive(PotionEffectsDefs.astralDistortion)) {
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).addChatMessage(new TextComponentString("The distortion around you prevents you from teleporting"));
            return true;
        }
        if (target.dimension == 1) {
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).addChatMessage(new TextComponentString("Nothing happens..."));
            return true;
        } else if (target.dimension == -1) {
            if (target instanceof PlayerEntity)
                ((PlayerEntity) target).addChatMessage(new TextComponentString("You are already in the nether."));
            return false;
        } else {
            DimensionUtilities.doDimensionTransfer((LivingEntity) target, -1);
            ArsMagica2.proxy.addDeferredDimensionTransfer((LivingEntity) target, -1);
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return new ItemStack[]{new ItemStack(ModItems.ENDER_ESSENCE.get())};
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 100; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "ghost", x, y - 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 2, 1);
                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
                particle.setMaxAge(25 + rand.nextInt(10));
                particle.setRGBColorF(0.7f, 0.2f, 0.2f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                Blocks.OBSIDIAN,
                Blocks.OBSIDIAN,
                Items.FLINT_AND_STEEL,
                Items.ENDER_PEARL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.4f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}