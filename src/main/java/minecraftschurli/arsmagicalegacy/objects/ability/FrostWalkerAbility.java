package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractToggledAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public final class FrostWalkerAbility extends AbstractToggledAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.5f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.ICE;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (!player.world.isRemote) {
            BlockPos pos = new BlockPos(player.getPosX(), Math.floor(player.getPosY()), player.getPosZ());
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++) {
                    BlockPos newPos = pos.add(x, -1, z);
                    if (player.world.getFluidState(newPos).getFluid() == Fluids.WATER || player.world.getFluidState(newPos).getFluid() == Fluids.FLOWING_WATER)
                        player.world.setBlockState(newPos, Blocks.FROSTED_ICE.getDefaultState());
                }
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }

    @Override
    protected boolean isEnabled(PlayerEntity player) {
        return CapabilityHelper.getAbilityState(player, this.getRegistryName());
    }
}
