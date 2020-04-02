package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;
import java.util.Set;

public final class WizardsAutumn extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isRemote) {
            for (int i = -SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); i <= SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); i++) {
                for (int j = -SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); j <= SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); j++) {
                    for (int k = -SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); k <= SpellUtil.modifyIntMul(2, stack, caster, null, world, SpellModifiers.RADIUS); k++) {
                        BlockPos pos = blockPos.add(i, j, k);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block instanceof LeavesBlock) {
                            if (block.removedByPlayer(state, world, pos, (PlayerEntity) caster, true, null)) {
                                block.onPlayerDestroy(world, pos, state);
                                block.harvestBlock(world, (PlayerEntity) caster, pos, state, null, stack);
                                //TODO play sound
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
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.NATURE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 15;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ItemTags.SAPLINGS),
                new ItemTagSpellIngredient(Tags.Items.INGOTS_IRON),
                new ItemTagSpellIngredient(Tags.Items.RODS_WOODEN),
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREEN_RUNE.get()))
        };
    }
}
