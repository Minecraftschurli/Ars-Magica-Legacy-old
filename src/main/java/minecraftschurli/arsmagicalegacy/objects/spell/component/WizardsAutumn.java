package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
                        if (block instanceof LeavesBlock && block.removedByPlayer(state, world, pos, (PlayerEntity) caster, true, null)) {
                            block.onPlayerDestroy(world, pos, state);
                            block.harvestBlock(world, (PlayerEntity) caster, pos, state, null, stack);
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
}
