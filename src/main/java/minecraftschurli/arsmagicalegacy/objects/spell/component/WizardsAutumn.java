package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;
import java.util.Random;

public class WizardsAutumn extends SpellComponent {
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREEN_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.WITCHWOOD_SAPLING.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.STICK)),
                new ItemTagSpellIngredient(Tags.Items.INGOTS_IRON)
        };
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isRemote) {
            int radius = 2;
            radius = SpellUtils.getModifiedIntMul(radius, stack, caster, null, world, SpellModifiers.RADIUS);
            for (int i = -radius; i <= radius; ++i) {
                for (int j = -radius; j <= radius; ++j) {
                    for (int k = -radius; k <= radius; ++k) {
                        BlockPos pos = blockPos.add(i, j, k);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block instanceof LeavesBlock) {
                            if (block.removedByPlayer(state, world, pos, (PlayerEntity) caster, true, null)) {
                                block.onPlayerDestroy(world, pos, state);
                                block.harvestBlock(world, (PlayerEntity) caster, pos, state, null, stack);
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
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NATURE);
//    }
//
//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS);
    }
}
