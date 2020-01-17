package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.Config;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.BurnoutCapability;
import minecraftschurli.arsmagicalegacy.capabilities.research.ResearchCapability;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.IResearchStorage;
import minecraftschurli.arsmagicalegacy.capabilities.magic.*;
import minecraftschurli.arsmagicalegacy.capabilities.mana.*;
import minecraftschurli.arsmagicalegacy.api.event.PlayerMagicLevelChangeEvent;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.network.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class MagicHelper {
    private static boolean setup = false;

    static void setup() {
        if (setup)
            return;
        setup = true;

        MinecraftForge.EVENT_BUS.addListener(MagicHelper::onPlayerLevelUp);
        MinecraftForge.EVENT_BUS.addListener(MagicHelper::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(MagicHelper::onPlayerLogin);
    }

    /**
     * Uses the given amount of mana and adds the given burnout, returns true if successful
     * @param entity      the entity to execute on
     * @param manaCost    the amount of mana to use
     * @param burnoutCost the amount of burnout to add
     * @return true if successful
     */
    public static boolean use(LivingEntity entity, float manaCost, float burnoutCost) {
        if (hasEnoughtMana(entity, manaCost) && !isBurnedOut(entity, burnoutCost)) {
            if (manaCost > 0)
                decreaseMana(entity, manaCost);
            if (burnoutCost > 0)
                increaseBurnout(entity, burnoutCost);
            return true;
        }
        return false;
    }

    //region =========XP=========

    /**
     * Adds the given amount of xp to the given player and levels up accordingly
     * @param player the player to increase the xp for
     * @param xp     the amount of xp to add
     */
    public static void addXP(PlayerEntity player, float xp) {
        IMagicStorage magic = getMagicCapability(player);
        xp += magic.getXp();
        while (xp >= magic.getMaxXP()){
            xp -= magic.getMaxXP();
            magic.levelUp();
            MinecraftForge.EVENT_BUS.post(new PlayerMagicLevelChangeEvent(player));
        }
        magic.setXp(xp);

        if (player instanceof ServerPlayerEntity) {
            syncMagic((ServerPlayerEntity) player);
        }
    }

    /**
     * Gets the maximum amount of magic-xp for the current level
     * @param player the player
     * @return the max magic-xp for the current level
     */
    public static float getMaxXP(PlayerEntity player) {
        return getMagicCapability(player).getMaxXP();
    }

    /**
     * Gets the current amount of magic-xp
     * @param player the player
     * @return the current amount of magic-xp
     */
    public static float getCurrentXP(PlayerEntity player) {
        return getMagicCapability(player).getXp();
    }

    /**
     * Gets the players current level
     * @param player the player
     * @return the current level
     */
    public static int getCurrentLevel(PlayerEntity player) {
        return getMagicCapability(player).getCurrentLevel();
    }

    //endregion

    //region =========BURNOUT=========

    /**
     * Gets the current burnout amount
     * @param entity the entity
     * @return the current burnout amount
     */
    public static float getBurnout(LivingEntity entity) {
        return getBurnoutCapability(entity).getBurnout();
    }

    /**
     * Gets the current maximum burnout amount
     * @param entity the entity
     * @return the current maximum burnout amount
     */
    public static float getMaxBurnout(LivingEntity entity) {
        return getBurnoutCapability(entity).getMaxBurnout();
    }

    /**
     * Checks if the entity is burned out (hasn't got enough capacity left)
     * @param entity      the entity
     * @param burnoutCost the burnout cost
     * @return true if the current burnout + the burnout cost is greater than the current maximum burnout
     */
    public static boolean isBurnedOut(LivingEntity entity, float burnoutCost) {
        return getBurnout(entity) + burnoutCost > getMaxBurnout(entity);
    }

    /**
     * Increase the burnout for the given entity
     * @param entity the entity to increase the burnout for
     * @param amount the amount to increase the burnout by
     */
    public static void increaseBurnout(LivingEntity entity, float amount) {
        getBurnoutCapability(entity).increase(amount);
        if (entity instanceof ServerPlayerEntity) {
            syncBurnout((ServerPlayerEntity) entity);
        }
    }

    /**
     * Decrease the burnout for the given entity
     * @param entity the entity to increase the burnout for
     * @param amount the amount to increase the burnout by
     */
    public static void decreaseBurnout(LivingEntity entity, float amount) {
        getBurnoutCapability(entity).decrease(amount);
        if (entity instanceof ServerPlayerEntity) {
            syncBurnout((ServerPlayerEntity) entity);
        }
    }

    //endregion

    //region =========MANA=========

    /**
     * Gets the current amount of mana
     * @param entity
     * @return
     */
    public static float getMana(LivingEntity entity) {
        return getManaCapability(entity).getMana();
    }

    /**
     *
     * @param entity
     * @return
     */
    public static float getMaxMana(LivingEntity entity) {
        //noinspection ConstantConditions
        EffectInstance potionEffect = entity.getActivePotionEffect(ModEffects.MANA_BOOST.get());
        float maxMana = getManaCapability(entity).getMaxMana();
        if (potionEffect != null)
            maxMana *= 1 + (0.25f * (potionEffect.getAmplifier() + 1));
        return maxMana;
    }

    /**
     *
     * @param entity
     * @param manaCost
     * @return
     */
    public static boolean hasEnoughtMana(LivingEntity entity, float manaCost) {
        return getMana(entity) >= manaCost;
    }

    /**
     *
     * @param entity
     * @return
     */
    public static int getManaRegenLevel(LivingEntity entity) {
        return getResearchCapability(entity)
                .getLearnedSkills()
                .stream()
                .map(Skill::getRegistryName)
                .filter(Objects::nonNull)
                .filter(skill -> skill.getPath().contains("mana_regen"))
                .map(skill -> skill.getPath().replace("mana_regen", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    /**
     * Increase the mana for the given entity
     * @param entity the entity to increase the mana for
     * @param amount the amount to increase the mana by
     */
    public static void increaseMana(LivingEntity entity, float amount) {
        getManaCapability(entity).increase(amount);
        if (entity instanceof ServerPlayerEntity) {
            syncMana((ServerPlayerEntity) entity);
        }
    }

    /**
     * Decrease the mana for the given entity
     * @param entity the entity to decrease the mana for
     * @param amount the amount to decrease the mana by
     */
    public static void decreaseMana(LivingEntity entity, float amount) {
        getManaCapability(entity).decrease(amount);
        if (entity instanceof ServerPlayerEntity) {
            syncMana((ServerPlayerEntity) entity);
        }
    }

    //endregion

    //region =========RESEARCH=========

    /**
     *
     * @param player
     * @param skillid
     */
    public static void learnSkill(PlayerEntity player, String skillid) {
        Skill skill = ArsMagicaAPI.getSkillRegistry().getValue(new ResourceLocation(skillid));
        IResearchStorage research = getResearchCapability(player);
        if (!research.canLearn(skill) || skill == null || research.get(skill.getPoint().getTier()) <= 0)
            return;
        research.learn(skill);
        research.use(skill.getPoint().getTier());
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    /**
     *
     * @param player
     * @param point
     * @return
     */
    public static int getSkillPoint(PlayerEntity player, SkillPoint point) {
        return getResearchCapability(player).get(point.getTier());
    }

    /**
     *
     * @param player
     * @param tier
     */
    public static void addSkillPoint(PlayerEntity player, int tier) {
        getResearchCapability(player).add(tier);
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    /**
     *
     * @param player
     * @param skill
     * @return
     */
    public static boolean knows(PlayerEntity player, Skill skill) {
        return getResearchCapability(player).knows(skill);
    }

    /**
     *
     * @param player
     * @param skill
     * @return
     */
    public static boolean knows(PlayerEntity player, ResourceLocation skill) {
        return getResearchCapability(player).knows(skill);
    }

    /**
     *
     * @param player
     * @param skill
     * @return
     */
    public static boolean canLearn(PlayerEntity player, Skill skill) {
        return getResearchCapability(player).canLearn(skill);
    }

    /**
     *
     * @param player
     * @return
     */
    public static List<ResourceLocation> getLearned(PlayerEntity player) {
        return getResearchCapability(player).getLearned();
    }

    /**
     *
     * @param player
     * @return
     */
    public static List<Skill> getLearnedSkills(PlayerEntity player) {
        return getResearchCapability(player).getLearnedSkills();
    }

    /**
     *
     * @param player
     * @param skill
     */
    public static void forget(PlayerEntity player, Skill skill) {
        getResearchCapability(player).forget(skill);
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    /**
     *
     * @param player
     */
    public static void forgetAll(PlayerEntity player) {
        getResearchCapability(player).forgetAll();
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    /**
     *
     * @param player
     * @param skill
     */
    public static void learn(PlayerEntity player, Skill skill) {
        getResearchCapability(player).learn(skill);
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    /**
     *
     * @param player
     * @param skill
     */
    public static void learn(PlayerEntity player, ResourceLocation skill) {
        getResearchCapability(player).learn(skill);
        if (player instanceof ServerPlayerEntity)
            syncResearch((ServerPlayerEntity) player);
    }

    //endregion

    //region =========EVENT=========

    private static void onPlayerLevelUp(final PlayerMagicLevelChangeEvent event) {
        PlayerEntity player = event.getPlayer();

        IManaStorage mana = getManaCapability(player);
        IBurnoutStorage burnout = getBurnoutCapability(player);
        IResearchStorage research = getResearchCapability(player);

        SkillPointRegistry.SKILL_POINT_REGISTRY.forEach((tier, point) -> {
            if (tier < 0)
                return;
            if (getCurrentLevel(player) >= point.getMinEarnLevel() && ((getCurrentLevel(player) - point.getMinEarnLevel()) % point.getLevelsForPoint()) == 0) {
                research.add(tier);
            }
        });

        mana.setMaxMana((float)(Math.pow(getCurrentLevel(player), 1.5f) * (85f * ((float)getCurrentLevel(player) / 100f)) + Config.COMMON.DEFAULT_MAX_MANA.get()));
        burnout.setMaxBurnout(getCurrentLevel(player) * 10 + 1);

        mana.setMana(getMaxMana(player));
        burnout.setBurnout(0);

        if (player instanceof ServerPlayerEntity) {
            syncResearch((ServerPlayerEntity) player);
            syncMana((ServerPlayerEntity) player);
            syncBurnout((ServerPlayerEntity) player);
        }
    }

    private static void onPlayerClone(final PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            PlayerEntity newPlayer = event.getPlayer();
            PlayerEntity oldPlayer = event.getOriginal();
            MagicHelper.getManaCapability(newPlayer).setFrom(MagicHelper.getManaCapability(oldPlayer));
            MagicHelper.getBurnoutCapability(newPlayer).setFrom(MagicHelper.getBurnoutCapability(oldPlayer));
            MagicHelper.getResearchCapability(newPlayer).setFrom(MagicHelper.getResearchCapability(oldPlayer));
            MagicHelper.getMagicCapability(newPlayer).setFrom(MagicHelper.getMagicCapability(oldPlayer));
        }
    }

    private static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            MagicHelper.syncMana(player);
            MagicHelper.syncBurnout(player);
            MagicHelper.syncResearch(player);
            MagicHelper.syncMagic(player);
        }
    }

    //endregion

    //region =========INTERNAL=========

    private static IManaStorage getManaCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(ManaCapability.MANA)
                .orElseThrow(() -> new IllegalStateException("No Mana Capability present!"));
    }

    private static IBurnoutStorage getBurnoutCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(BurnoutCapability.BURNOUT)
                .orElseThrow(() -> new IllegalStateException("No Burnout Capability present!"));
    }

    private static IResearchStorage getResearchCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(ResearchCapability.RESEARCH)
                .orElseThrow(() -> new IllegalStateException("No Research Capability present!"));
    }

    private static IMagicStorage getMagicCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(MagicCapability.MAGIC)
                .orElseThrow(() -> new IllegalStateException("No Magic Capability present!"));
    }

    private static void syncMana(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        IManaStorage iManaStorage = getManaCapability(player);
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncManaPacket(
                        iManaStorage.getMana(),
                        iManaStorage.getMaxMana()
                ));
    }

    private static void syncBurnout(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        IBurnoutStorage iBurnoutStorage = getBurnoutCapability(player);
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBurnoutPacket(
                        iBurnoutStorage.getBurnout(),
                        iBurnoutStorage.getMaxBurnout()
                )
        );
    }

    private static void syncResearch(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        IResearchStorage iStorage = getResearchCapability(player);
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncResearchPacket(iStorage)
        );
    }

    private static void syncMagic(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        IMagicStorage iStorage = getMagicCapability(player);
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncMagicPacket(iStorage.getCurrentLevel())
        );
    }

    //endregion
}
