package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class PhotosynthesisAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 1;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.NATURE;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if(player.world.isRemote) return;
        player.addExhaustion(player.world.canBlockSeeSky(player.getPosition()) && player.world.isDaytime() && !player.world.isRainingAt(player.getPosition()) ? -0.025f : 0.025f);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
