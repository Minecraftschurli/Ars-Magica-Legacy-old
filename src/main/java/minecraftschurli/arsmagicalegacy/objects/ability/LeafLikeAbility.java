package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public final class LeafLikeAbility extends AbstractAffinityAbility {
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
        if (player.collidedHorizontally) {
            if (player.isSneaking())
                player.setMotion(player.getMotion().x, player.getMotion().y * 0.8, player.getMotion().z);
            else {
                player.move(MoverType.PLAYER, new Vec3d(0, 0.25f, 0));
                player.setMotion(player.getMotion().x, 0, player.getMotion().z);
            }
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.TICK);
        return types;
    }
}
