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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Drought extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof FlowerBlock || block instanceof TallGrassBlock) {
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            return true;
        } else if (block == Blocks.GRASS || block == Blocks.MYCELIUM || block == Blocks.SANDSTONE || block == Blocks.DIRT) {
            world.setBlockState(pos, Blocks.SAND.getDefaultState());
            return true;
        } else if (block == Blocks.STONE) {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            return true;
        } else if (block == Blocks.STONE_BRICKS) {
            world.setBlockState(pos, Blocks.CRACKED_STONE_BRICKS.getDefaultState());
            return true;
        }
        if (block == Blocks.WATER) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            return true;
        } else {
            pos = pos.offset(blockFace);
            block = world.getBlockState(pos).getBlock();
            if (block == Blocks.WATER) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return true;
            }
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 60;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "ember", x + 0.5, y + 1, z + 0.5);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
//                particle.setAffectedByGravity();
//                particle.setRGBColorF(0.9f, 0.8f, 0.5f);
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.FIRE.get(), ModSpellParts.AIR.get());
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ORANGE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.DEAD_BUSH)),
                new ItemStackSpellIngredient(new ItemStack(Items.SAND))
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return affinity == ModSpellParts.FIRE.get() ? 0.008f : 0.004f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
