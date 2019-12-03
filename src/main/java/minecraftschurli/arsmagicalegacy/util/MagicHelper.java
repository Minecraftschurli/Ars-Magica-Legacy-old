package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.api.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.magic.CapabilityMagic;
import minecraftschurli.arsmagicalegacy.capabilities.magic.IMagicStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.network.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
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
                new SyncManaPacket(
                        iManaStorage.getMana(),
                        iManaStorage.getMaxMana()
                )
        ));
    }

    public static void syncBurnout(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBurnoutPacket(
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
                        new SyncResearchPacket(iStorage)
                )
        );
    }

    public static void syncMagic(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityMagic.MAGIC).ifPresent(iStorage ->
                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new SyncMagicPacket(iStorage.getCurrentLevel())
                )
        );
    }

    public static void regenMana(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> iManaStorage.increase(amount));
            syncMana((ServerPlayerEntity) player);
        }
    }

    public static void regenBurnout(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> iBurnoutStorage.decrease(amount));
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
        return getMana(caster) >= manaCost;
    }

    public static int getCurrentLevel(LivingEntity player) {
        return getMagicCapability(player).getCurrentLevel();
    }

    public static IManaStorage getManaCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(CapabilityMana.MANA)
                .orElseThrow(() -> new IllegalStateException("No Mana Capability present!"));
    }

    public static IBurnoutStorage getBurnoutCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(CapabilityBurnout.BURNOUT)
                .orElseThrow(() -> new IllegalStateException("No Burnout Capability present!"));
    }

    public static IResearchStorage getResearchCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(CapabilityResearch.RESEARCH)
                .orElseThrow(() -> new IllegalStateException("No Research Capability present!"));
    }

    public static IMagicStorage getMagicCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(CapabilityMagic.MAGIC)
                .orElseThrow(() -> new IllegalStateException("No Magic Capability present!"));
    }

    public static void learnSkill(PlayerEntity player, String skillid) {
        Skill skill = SpellRegistry.SKILL_REGISTRY.getValue(new ResourceLocation(skillid));
        IResearchStorage research = getResearchCapability(player);
        if (!research.canLearn(skill) || skill == null || research.get(skill.getPoint().getTier()) <= 0)
            return;
        research.learn(skill);
        research.use(skill.getPoint().getTier());
        syncResearch((ServerPlayerEntity) player);
    }

    public static int getSkillPoint(PlayerEntity player, SkillPoint point) {
        return getResearchCapability(player).get(point.getTier());
    }

    public static void addSkillPoint(LivingEntity entity, int tier) {
        getResearchCapability(entity).add(tier);
        if (entity instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) entity);
    }
}
