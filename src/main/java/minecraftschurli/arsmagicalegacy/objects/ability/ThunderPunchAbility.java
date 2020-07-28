package minecraftschurli.arsmagicalegacy.objects.ability;

import java.util.Collection;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class ThunderPunchAbility extends AbstractAffinityAbility {
    @Override
    public float getMinimumDepth() {
        return 0.75f;
    }

    @Override
    public ResourceLocation getAffinity() {
        return Affinity.LIGHTNING;
    }

    @Override
    public void applyHurting(PlayerEntity player, LivingHurtEvent event) {
        if(!player.world.isRemote && player.getHeldItemMainhand() == ItemStack.EMPTY) player.world.addEntity(new LightningBoltEntity(player.world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), false));
    }

    @Override
    public Collection<AbilityListenerType> registerListeners(Collection<AbilityListenerType> types) {
        types.add(AbilityListenerType.HURTING);
        return types;
    }
}
