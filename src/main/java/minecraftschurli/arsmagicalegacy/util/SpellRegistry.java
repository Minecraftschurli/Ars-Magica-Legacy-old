package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.spell.skill.SkillTree;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Contains all spell parts, used for both registration<BR>
 * Skill are automatically created when doing any thing
 *
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SpellRegistry {
    private static final List<AbstractSpellPart> SPELL_PARTS = new ArrayList<>();
    private static final List<Supplier<Skill>> SKILLS = new ArrayList<>();
    public static IForgeRegistry<AbstractSpellPart> SPELL_PART_REGISTRY = null;
    public static IForgeRegistry<Skill> SKILL_REGISTRY = null;
    public static IForgeRegistry<SkillPoint> SKILL_POINT_REGISTRY = null;
    public static IForgeRegistry<SkillTree> SKILL_TREE_REGISTRY = null;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PART_REGISTRY = new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_parts")).setType(AbstractSpellPart.class).create();
        SKILL_REGISTRY = new RegistryBuilder<Skill>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skills")).setType(Skill.class).create();
        SKILL_POINT_REGISTRY = new RegistryBuilder<SkillPoint>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skill_points")).setType(SkillPoint.class).create();
        SKILL_TREE_REGISTRY = new RegistryBuilder<SkillTree>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skill_trees")).setType(SkillTree.class).create();

        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEntities.register();
        ModParticles.register();
        ModEffects.register();
        ModBiomes.register();
        ModContainers.register();
        SpellParts.register();
    }

    @SubscribeEvent
    public static void onSpellPartRegister(RegistryEvent.Register<AbstractSpellPart> event) {
        event.getRegistry().registerAll(SPELL_PARTS.toArray(new AbstractSpellPart[0]));
    }

    @SubscribeEvent
    public static void onSkillRegister(RegistryEvent.Register<Skill> event) {
        event.getRegistry().registerAll(SKILLS.stream().map(Supplier::get).toArray(Skill[]::new));
    }

    /**
     * Register a spell component
     *
     * @param id      : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Component, use new {@link SpellComponent} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(ResourceLocation id, Supplier<SkillPoint> tier, SpellComponent part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Supplier<Skill> skill = () -> new Skill(getComponentIcon(id), tier.get(), posX, posY, tree.get(), parents).setRegistryName(id);
        SKILLS.add(skill);
        return RegistryObject.of(id, SPELL_PART_REGISTRY);
    }

    private static ResourceLocation getComponentIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/component/"+id.getPath()+".png");
    }

    /**
     * Register a spell component
     *
     * @param name    : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Component, use new {@link SpellComponent} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(String modid, String name, Supplier<SkillPoint> tier, SpellComponent part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellComponent(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell modifier
     *
     * @param id      : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(ResourceLocation id, Supplier<SkillPoint> tier, SpellModifier part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Supplier<Skill> skill = () -> new Skill(getModifierIcon(id), tier.get(), posX, posY, tree.get(), parents).setRegistryName(id);
        SKILLS.add(skill);
        return RegistryObject.of(id, SPELL_PART_REGISTRY);
    }

    private static ResourceLocation getModifierIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/modifier/"+id.getPath()+".png");
    }

    /**
     * Register a spell modifier
     *
     * @param name    : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(String modid, String name, Supplier<SkillPoint> tier, SpellModifier part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellModifier(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell shape
     *
     * @param id      : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Shape, use new {@link SpellShape} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(ResourceLocation id, Supplier<SkillPoint> tier, SpellShape part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        Supplier<Skill> skill = () -> new Skill(getShapeIcon(id), tier.get(), posX, posY, tree.get(), parents).setRegistryName(id);
        SKILLS.add(skill);
        return RegistryObject.of(id, SPELL_PART_REGISTRY);
    }

    private static ResourceLocation getShapeIcon(ResourceLocation id) {
        if (id.getPath().equals("null")) return null;
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/shape/"+id.getPath()+".png");
    }

    /**
     * Register a spell shape
     *
     * @param name    : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Shape, use new {@link SpellShape} ()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(String modid, String name, Supplier<SkillPoint> tier, SpellShape part, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSpellShape(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    public static Skill getSkillFromPart(AbstractSpellPart part) {
        return SKILL_REGISTRY.getValue(part.getRegistryName());
    }

    public static AbstractSpellPart getPartByRecipe(ArrayList<ItemStack> currentAddedItems) {
        //TODO @minecraftschurli
        /*for (AbstractSpellPart data : SPELL_PART_REGISTRY.getValues()) {
            if (data != null && data.getRecipe() != null) {
                List<ItemStack> convRecipe = RecipeUtils.getConvRecipe(data);
                boolean match = currentAddedItems.size() == convRecipe.size();
                if (!match) continue;
                for (int i = 0; i < convRecipe.size(); i++) {
                    //match &= OreDictionary.itemMatches(convRecipe.get(i), currentAddedItems.get(i), false);
                    match &= convRecipe.get(i).hasTag() || (!currentAddedItems.get(i).hasTag() && NBTUtils.contains(convRecipe.get(i).getTag(), currentAddedItems.get(i).getTag()));
                    if (!match) break;
                }
                if (!match) ArsMagicaLegacy.LOGGER.debug("Part doesn't match {}", data.getRegistryName().toString());
                if (!match) continue;
                ArsMagicaLegacy.LOGGER.debug("Part matches : {}!", data.getRegistryName().toString());
                return data;
            }
        }*/
        return null;
    }

    public static SpellShape getShapeFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellShape ? (SpellShape) part : null;
    }

    public static SpellModifier getModifierFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellModifier ? (SpellModifier) part : null;
    }

    public static SpellComponent getComponentFromName(String shapeName) {
        AbstractSpellPart part = SPELL_PART_REGISTRY.getValue(new ResourceLocation(shapeName));
        return part instanceof SpellComponent ? (SpellComponent) part : null;
    }

    public static List<Skill> getSkillsForTree(SkillTree tree) {
        return SKILL_REGISTRY.getValues().stream().filter(skill -> skill.getTree() == tree).collect(Collectors.toList());
    }

    public static SkillPoint getSkillFromName(String name) {
        return SKILL_POINT_REGISTRY.getValue(new ResourceLocation(name));
    }
}
