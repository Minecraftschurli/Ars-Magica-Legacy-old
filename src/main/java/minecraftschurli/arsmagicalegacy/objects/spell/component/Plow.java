package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Plow extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        //Block blockAbove = world.getBlockState(pos.up()).getBlock();
        if ((block == Blocks.DIRT || block == Blocks.GRASS)) {
			/*if (!(blockAbove instanceof BlockFlower)){
				return false;
			}
			blockAbove.breakBlock(world, blockx, blocky+1, blockz, blockAbove, 0);
			world.setBlock(blockx, blocky+1, blockz, Blocks.air);*/
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            }
            return true;
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
        return 75;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 10; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "rock", x, y + 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.2f, rand.nextDouble() * 0.2 - 0.1);
                particle.setDontRequireControllers();
                particle.setAffectedByGravity();
                particle.setMaxAge(20);
                particle.setParticleScale(0.05f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.EARTH);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.GREEN_RUNE.get()),
                Items.STONE_HOE
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}