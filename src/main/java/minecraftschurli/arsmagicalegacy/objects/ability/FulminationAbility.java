package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public final class FulminationAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIGHTNING;
    }

    @Override
    public float getMaximumDepth() {
        return 0.95f;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (!player.world.isRemote) {
            if (CapabilityHelper.getAffinityDepth(player, Affinity.LIGHTNING) >= 0.8f) {
                BlockPos offsetPos = new BlockPos(player.getPosX() - 5 + player.getRNG().nextInt(11), player.getPosY() - 5 + player.getRNG().nextInt(11), player.getPosZ() - 5 + player.getRNG().nextInt(11));
                BlockState block = player.world.getBlockState(offsetPos);
                if (block.getBlock() == Blocks.TNT) {
                    player.world.setBlockState(offsetPos, Blocks.AIR.getDefaultState());
                    Blocks.TNT.catchFire(block, player.world, offsetPos, null, player);
                }
            }
            if (CapabilityHelper.getAffinityDepth(player, Affinity.LIGHTNING) >= 0.7f && player.getRNG().nextDouble() < 0.05f) {
                List<CreeperEntity> creepers = player.world.getEntitiesWithinAABB(CreeperEntity.class, player.getBoundingBox().expand(5, 5, 5));
                for (CreeperEntity creeper : creepers) {
                    creeper.onStruckByLightning(null);
//                    ParticleUtil.boltFromEntityToEntity(player.world, player, player, creeper, 0, 1, -1);
                }
            }
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
