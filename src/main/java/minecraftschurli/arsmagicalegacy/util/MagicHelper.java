package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.network.SyncBurnout;
import minecraftschurli.arsmagicalegacy.network.SyncMana;
import minecraftschurli.arsmagicalegacy.network.SyncResearch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class MagicHelper {
    public static boolean use(PlayerEntity player, float manaCost, float burnoutCost) {
        if (player instanceof ServerPlayerEntity) {
            LazyOptional<IManaStorage> manaStorage = player.getCapability(CapabilityMana.MANA);
            LazyOptional<IBurnoutStorage> burnoutStorage = player.getCapability(CapabilityBurnout.BURNOUT);
            if (manaStorage.map(IManaStorage::getMana).orElse(0.0f) >= manaCost && burnoutStorage.map(iBurnoutStorage -> iBurnoutStorage.getMaxBurnout() - iBurnoutStorage.getBurnout()).orElse(0.0f) >= burnoutCost) {
                manaStorage.ifPresent(iManaStorage -> iManaStorage.decrease(manaCost));
                burnoutStorage.ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(burnoutCost));
                syncMana((ServerPlayerEntity) player);
                syncBurnout((ServerPlayerEntity) player);
                return true;
            }
        }
        return false;
    }

    public static void syncMana(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncMana(
                        iManaStorage.getMana(),
                        iManaStorage.getMaxMana()
                )
        ));
    }

    public static void syncBurnout(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBurnout(
                        iBurnoutStorage.getBurnout(),
                        iBurnoutStorage.getMaxBurnout()
                )
        ));
    }

    public static void syncResearch(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityResearch.RESEARCH).ifPresent(iStorage ->
                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new SyncResearch(iStorage)
                )
        );
    }

    public static void regenMana(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> {
                iManaStorage.increase(amount);
            });
            syncMana((ServerPlayerEntity) player);
        }
    }

    public static void regenBurnout(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> {
                iBurnoutStorage.decrease(amount);
            });
            syncBurnout((ServerPlayerEntity) player);
        }
    }

    public static float getBurnout(LivingEntity caster) {
        return getBurnoutCapability(caster).getBurnout();
    }

    public static float getMaxBurnout(LivingEntity caster) {
        return getBurnoutCapability(caster).getMaxBurnout();
    }

    public static float getMana(LivingEntity caster) {
        return getManaCapability(caster).getMana();
    }

    public static float getMaxMana(LivingEntity caster) {
        return getManaCapability(caster).getMaxMana();
    }

    public static boolean hasEnoughtMana(LivingEntity caster, float manaCost) {
        return getManaCapability(caster).getMana() >= manaCost;
    }

    public static IManaStorage getManaCapability(LivingEntity entity) {
        return entity.getCapability(CapabilityMana.MANA)
                .orElseThrow(() -> new IllegalStateException("No Mana Capability present!"));
    }

    public static IBurnoutStorage getBurnoutCapability(LivingEntity entity) {
        return entity.getCapability(CapabilityBurnout.BURNOUT)
                .orElseThrow(() -> new IllegalStateException("No Burnout Capability present!"));
    }

    public static IResearchStorage getResearchCapability(LivingEntity entity) {
        return entity.getCapability(CapabilityResearch.RESEARCH)
                .orElseThrow(() -> new IllegalStateException("No Research Capability present!"));
    }
}
