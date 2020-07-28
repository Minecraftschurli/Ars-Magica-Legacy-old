package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public final class LungsAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.4f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.WATER;
    }

    @Override
    public void applyTick(PlayerEntity player) {
        if (player.isInWater() && player.world.rand.nextInt(20) < 4) player.setAir(player.getAir() + 1);
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
