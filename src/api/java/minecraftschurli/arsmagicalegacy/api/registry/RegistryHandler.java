package minecraftschurli.arsmagicalegacy.api.registry;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.rituals.AbstractRitual;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * @author Minecraftschurli
 * @version 2020-03-04
 */
public class RegistryHandler {
    private static ForgeRegistry<SkillTree> SKILL_TREE_REGISTRY = null;
    private static ForgeRegistry<AbstractSpellPart> SPELL_PART_REGISTRY = null;
    private static ForgeRegistry<Skill> SKILL_REGISTRY = null;
    private static ForgeRegistry<Affinity> AFFINITY_REGISTRY = null;
    private static ForgeRegistry<EtheriumType> ETHERIUM_REGISTRY = null;
    private static ForgeRegistry<AbstractAffinityAbility> AFFINITY_ABILITY_REGISTRY = null;
    private static ForgeRegistry<AbstractRitual> RITUAL_REGISTRY = null;

    /**
     * Don't call this method yourself
     */
    public static void setup() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(SpellRegistry::onSpellPartRegister);
        modEventBus.addListener(SkillRegistry::onSkillRegister);
        modEventBus.addListener(SkillTreeRegistry::onSkillTreeRegister);
        modEventBus.addListener(AffinityRegistry::onAffinityRegister);
        modEventBus.addListener(EtheriumRegistry::onEtheriumRegister);
        modEventBus.addListener(RitualRegistry::onRitualRegister);
        modEventBus.addListener(AbilityRegistry::onAbilityRegister);
        modEventBus.addListener(RegistryHandler::registerRegistries);
    }

    private static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PART_REGISTRY = (ForgeRegistry<AbstractSpellPart>) new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "spell_parts")).setType(AbstractSpellPart.class).create();
        SKILL_REGISTRY = (ForgeRegistry<Skill>) new RegistryBuilder<Skill>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "skills")).setType(Skill.class).create();
        SKILL_TREE_REGISTRY = (ForgeRegistry<SkillTree>) new RegistryBuilder<SkillTree>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "skill_trees")).setType(SkillTree.class).create();
        AFFINITY_REGISTRY = (ForgeRegistry<Affinity>) new RegistryBuilder<Affinity>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "affinities")).setType(Affinity.class).create();
        ETHERIUM_REGISTRY = (ForgeRegistry<EtheriumType>) new RegistryBuilder<EtheriumType>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "etherium_types")).legacyName(new ResourceLocation(ArsMagicaAPI.MODID, "etherium")).setType(EtheriumType.class).create();
        AFFINITY_ABILITY_REGISTRY = (ForgeRegistry<AbstractAffinityAbility>) new RegistryBuilder<AbstractAffinityAbility>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "affinity_abilities")).setType(AbstractAffinityAbility.class).create();
        RITUAL_REGISTRY = (ForgeRegistry<AbstractRitual>) new RegistryBuilder<AbstractRitual>().setName(new ResourceLocation(ArsMagicaAPI.MODID, "rituals")).setType(AbstractRitual.class).create();
    }

    /**
     * Gets the Registry for {@link AbstractSpellPart SpellParts}
     *
     * @return the Registry for {@link AbstractSpellPart SpellParts}
     */
    public static ForgeRegistry<AbstractSpellPart> getSpellPartRegistry() {
        return SPELL_PART_REGISTRY;
    }

    /**
     * Gets the Registry for {@link Skill Skills}
     *
     * @return the Registry for {@link Skill Skills}
     */
    public static ForgeRegistry<Skill> getSkillRegistry() {
        return SKILL_REGISTRY;
    }

    /**
     * Gets the Registry for {@link SkillTree SkillTrees}
     *
     * @return the Registry for {@link SkillTree SkillTrees}
     */
    public static ForgeRegistry<SkillTree> getSkillTreeRegistry() {
        return SKILL_TREE_REGISTRY;
    }

    /**
     * Gets the Registry for {@link Affinity Affinities}
     *
     * @return the Registry for {@link Affinity Affinities}
     */
    public static ForgeRegistry<Affinity> getAffinityRegistry() {
        return AFFINITY_REGISTRY;
    }

    /**
     * Gets the Registry for {@link EtheriumType}
     *
     * @return the Registry for {@link EtheriumType}
     */
    public static ForgeRegistry<EtheriumType> getEtheriumRegistry() {
        return ETHERIUM_REGISTRY;
    }

    /**
     * Gets the Registry for {@link AbstractAffinityAbility}
     *
     * @return the Registry for {@link AbstractAffinityAbility}
     */
    public static ForgeRegistry<AbstractAffinityAbility> getAffinityAbilityRegistry() {
        return AFFINITY_ABILITY_REGISTRY;
    }

    /**
     * Gets the Registry for {@link AbstractRitual}
     *
     * @return the Registry for {@link AbstractRitual}
     */
    public static ForgeRegistry<AbstractRitual> getRitualRegistry() {
        return RITUAL_REGISTRY;
    }
}
