package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class FireAspectAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.8f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.FIRE;
    }

    @Override
    public void applyHurting(PlayerEntity player, LivingHurtEvent event) {
        if (!player.world.isRemote && player.getHeldItemMainhand() == ItemStack.EMPTY) {
            event.getEntityLiving().setFire(4);
            event.setAmount(event.getAmount() + 3);
        }
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURTING);
        return types;
    }
}
