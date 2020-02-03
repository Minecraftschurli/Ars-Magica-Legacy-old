package minecraftschurli.arsmagicalegacy.api.capability;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.Config;
import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.event.PlayerMagicLevelChangeEvent;
import minecraftschurli.arsmagicalegacy.api.network.*;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-11-20
 */
public class CapabilityHelper {
    private static boolean setup = false;

    public static void setup() {
        if (setup)
            return;
        setup = true;

        MinecraftForge.EVENT_BUS.addListener(CapabilityHelper::onPlayerLevelUp);
        MinecraftForge.EVENT_BUS.addListener(CapabilityHelper::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(CapabilityHelper::onPlayerLogin);
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
        EffectInstance potionEffect = entity.getActivePotionEffect(ForgeRegistries.POTIONS.getValue(new ResourceLocation(ArsMagicaAPI.MODID, "mana_boost")));
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
        if (skill == null) {
            return;
        }
        IResearchStorage research = getResearchCapability(player);
        if (!player.isCreative()) {
            if (!research.canLearn(skill) || research.get(skill.getPoint().getTier()) <= 0) {
                return;
            }
        }
        research.learn(skill);
        if (!player.isCreative())
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
            CapabilityHelper.getManaCapability(newPlayer).setFrom(CapabilityHelper.getManaCapability(oldPlayer));
            CapabilityHelper.getBurnoutCapability(newPlayer).setFrom(CapabilityHelper.getBurnoutCapability(oldPlayer));
            CapabilityHelper.getResearchCapability(newPlayer).setFrom(CapabilityHelper.getResearchCapability(oldPlayer));
            CapabilityHelper.getMagicCapability(newPlayer).setFrom(CapabilityHelper.getMagicCapability(oldPlayer));
        }
    }

    private static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            CapabilityHelper.syncMana(player);
            CapabilityHelper.syncBurnout(player);
            CapabilityHelper.syncResearch(player);
            CapabilityHelper.syncMagic(player);
        }
    }

    //endregion

    //region =========INTERNAL=========

    @CapabilityInject(IRiftStorage.class)
    static Capability<IRiftStorage> RIFT_STORAGE = null;
    @CapabilityInject(IResearchStorage.class)
    static Capability<IResearchStorage> RESEARCH = null;
    @CapabilityInject(IManaStorage.class)
    static Capability<IManaStorage> MANA = null;
    @CapabilityInject(IMagicStorage.class)
    static Capability<IMagicStorage> MAGIC = null;
    @CapabilityInject(IBurnoutStorage.class)
    static Capability<IBurnoutStorage> BURNOUT = null;

    public static Capability<IRiftStorage> getRiftStorageCapability() {
        return RIFT_STORAGE;
    }

    public static Capability<IResearchStorage> getResearchCapability() {
        return RESEARCH;
    }

    public static Capability<IManaStorage> getManaCapability() {
        return MANA;
    }

    public static Capability<IMagicStorage> getMagicCapability() {
        return MAGIC;
    }

    public static Capability<IBurnoutStorage> getBurnoutCapability() {
        return BURNOUT;
    }

    private static IManaStorage getManaCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(MANA)
                .orElseThrow(() -> new IllegalStateException("No Mana Capability present!"));
    }

    private static IBurnoutStorage getBurnoutCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(BURNOUT)
                .orElseThrow(() -> new IllegalStateException("No Burnout Capability present!"));
    }

    private static IResearchStorage getResearchCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(RESEARCH)
                .orElseThrow(() -> new IllegalStateException("No Research Capability present!"));
    }

    private static IMagicStorage getMagicCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(MAGIC)
                .orElseThrow(() -> new IllegalStateException("No Magic Capability present!"));
    }

    private static IRiftStorage getRiftStorageCapability(LivingEntity entity) {
        Objects.requireNonNull(entity);
        return entity.getCapability(RIFT_STORAGE)
                .orElseThrow(() -> new IllegalStateException("No Rift Storage Capability present!"));
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
