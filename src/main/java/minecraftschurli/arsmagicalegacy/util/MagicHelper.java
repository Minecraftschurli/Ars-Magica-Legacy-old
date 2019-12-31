package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.*;
import minecraftschurli.arsmagicalegacy.capabilities.magic.*;
import minecraftschurli.arsmagicalegacy.capabilities.mana.*;
import minecraftschurli.arsmagicalegacy.capabilities.research.*;
import minecraftschurli.arsmagicalegacy.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.network.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class MagicHelper {
    public static boolean use(LivingEntity player, float manaCost, float burnoutCost) {
        if (hasEnoughtMana(player, manaCost) && !isBurnedOut(player, burnoutCost)) {
            decreaseMana(player, manaCost);
            increaseBurnout(player, burnoutCost);
            return true;
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

    public static void increaseMana(LivingEntity livingEntity, float amount) {
        livingEntity.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> iManaStorage.increase(amount));
        if (livingEntity instanceof ServerPlayerEntity) {
            syncMana((ServerPlayerEntity) livingEntity);
        }
    }

    public static void decreaseMana(LivingEntity livingEntity, float amount) {
        livingEntity.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> iManaStorage.decrease(amount));
        if (livingEntity instanceof ServerPlayerEntity) {
            syncMana((ServerPlayerEntity) livingEntity);
        }
    }

    public static void increaseBurnout(LivingEntity livingEntity, float amount) {
        livingEntity.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(amount));
        if (livingEntity instanceof ServerPlayerEntity) {
            syncBurnout((ServerPlayerEntity) livingEntity);
        }
    }

    public static void decreaseBurnout(LivingEntity livingEntity, float amount) {
        livingEntity.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> iBurnoutStorage.decrease(amount));
        if (livingEntity instanceof ServerPlayerEntity) {
            syncBurnout((ServerPlayerEntity) livingEntity);
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
        Skill skill = ArsMagicaAPI.getSkillRegistry().getValue(new ResourceLocation(skillid));
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

    public static boolean isBurnedOut(LivingEntity caster, float burnoutCost) {
        IBurnoutStorage storage = getBurnoutCapability(caster);
        return storage.getBurnout() + burnoutCost > storage.getMaxBurnout();
    }

    public static int getManaRegenLevel(PlayerEntity player) {
        return getResearchCapability(player)
                .getLearnedSkills()
                .stream()
                .filter(skill -> skill.getID().contains("mana_regen"))
                .map(Skill::getID)
                .map(s -> s.replace("mana_regen", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }
}
