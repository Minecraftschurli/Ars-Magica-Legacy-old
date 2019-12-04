package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class WizardsAutumn extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                Blocks.SAPLING,
                new ItemStack(ModItems.GREEN_RUNE.get()),
                Items.STICK,
                Items.IRON_INGOT
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isRemote) {
            int radius = 2;
            radius = SpellUtils.getModifiedIntMul(radius, stack, caster, null, world, SpellModifiers.RADIUS);
            for (int i = -radius; i <= radius; ++i) {
                for (int j = -radius; j <= radius; ++j) {
                    for (int k = -radius; k <= radius; ++k) {
                        BlockPos pos = blockPos.add(i, j, k);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block != null && block.isLeaves(state, world, pos)) {
                            if (block.removedByPlayer(state, world, pos, DummyPlayerEntity.fromEntityLiving(caster), true)) {
                                block.onBlockDestroyedByPlayer(world, pos, state);
                                block.harvestBlock(world, DummyPlayerEntity.fromEntityLiving(caster), pos, state, null, stack);
                                //TODO : play sound
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return applyEffectBlock(stack, world, target.getPosition(), null, target.posX, target.posY, target.posZ, caster);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NATURE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS);
    }
}