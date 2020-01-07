package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.*;

import java.util.*;

/**
 * Contains all spell parts, used for both registration<BR>
 * Skill are automatically created when doing any thing
 *
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SpellRegistry {
    private static final List<AbstractSpellPart> SPELL_PARTS = new ArrayList<>();

    static void onSpellPartRegister(RegistryEvent.Register<AbstractSpellPart> event) {
        if (event.getGenericType() != AbstractSpellPart.class)
            return;
        event.getRegistry().registerAll(SPELL_PARTS.toArray(new AbstractSpellPart[0]));
    }

    /**
     * Register a spell component
     *
     * @param id      : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Component, use new {@link SpellComponent}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(ResourceLocation id, SkillPoint tier, SpellComponent part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        SkillRegistry.registerSkill(id, getComponentIcon(id), tier, tree, posX, posY, parents);
        return RegistryObject.of(id, ArsMagicaAPI.getSpellPartRegistry());
    }

    /**
     * Register a spell component
     *
     * @param name    : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Component, use new {@link SpellComponent}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(String modid, String name, SkillPoint tier, SpellComponent part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellComponent(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell component
     *
     * @param name    : Name of this component
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Component, use new {@link SpellComponent}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellComponent> registerSpellComponent(String name, SkillPoint tier, SpellComponent part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellComponent(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell modifier
     *
     * @param id      : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(ResourceLocation id, SkillPoint tier, SpellModifier part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        SkillRegistry.registerSkill(id, getModifierIcon(id), tier, tree, posX, posY, parents);
        return RegistryObject.of(id, ArsMagicaAPI.getSpellPartRegistry());
    }

    /**
     * Register a spell modifier
     *
     * @param name    : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(String modid, String name, SkillPoint tier, SpellModifier part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellModifier(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell modifier
     *
     * @param name    : Name of this modifier
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Modifier, use new {@link SpellModifier}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellModifier> registerSpellModifier(String name, SkillPoint tier, SpellModifier part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellModifier(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell shape
     *
     * @param id      : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Shape, use new {@link SpellShape}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(ResourceLocation id, SkillPoint tier, SpellShape part, SkillTree tree, int posX, int posY, String... parents) {
        part.setRegistryName(id);
        SPELL_PARTS.add(part);
        SkillRegistry.registerSkill(id, getShapeIcon(id), tier, tree, posX, posY, parents);
        return RegistryObject.of(id, ArsMagicaAPI.getSpellPartRegistry());
    }

    /**
     * Register a spell shape
     *
     * @param name    : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Shape, use new {@link SpellShape}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(String modid, String name, SkillPoint tier, SpellShape part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellShape(new ResourceLocation(modid, name), tier, part, tree, posX, posY, parents);
    }

    /**
     * Register a spell shape
     *
     * @param name    : Name of this shape
     * @param tier    : Skill Point required to unlock
     * @param part    : Actual Shape, use new {@link SpellShape}()
     * @param tree    : Skill Tree
     * @param posX    : Position in the tree
     * @param posY    : Position in the tree
     * @param parents : Skills that need to be unlocked before this one (occulus only)
     */
    public static RegistryObject<SpellShape> registerSpellShape(String name, SkillPoint tier, SpellShape part, SkillTree tree, int posX, int posY, String... parents) {
        return registerSpellShape(ModLoadingContext.get().getActiveNamespace(), name, tier, part, tree, posX, posY, parents);
    }

    private static ResourceLocation getComponentIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/component/" + id.getPath() + ".png");
    }

    private static ResourceLocation getModifierIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/modifier/" + id.getPath() + ".png");
    }

    private static ResourceLocation getShapeIcon(ResourceLocation id) {
        if (id.getPath().equals("null")) return null;
        return new ResourceLocation(id.getNamespace(), "textures/icon/spell/shape/" + id.getPath() + ".png");
    }

    public static Skill getSkillFromPart(AbstractSpellPart part) {
        return ArsMagicaAPI.getSkillRegistry().getValue(part.getRegistryName());
    }

    /*public static AbstractSpellPart getPartByRecipe(List<ItemStack> currentAddedItems) {
        //TODO @minecraftschurli
        *//*for (AbstractSpellPart data : SPELL_PART_REGISTRY.getValues()) {
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
        }*//*
        return null;
    }*/

    public static SpellShape getShapeFromName(String shapeName) {
        ResourceLocation rl = ResourceLocation.tryCreate(shapeName);
        AbstractSpellPart part = ArsMagicaAPI.getSpellPartRegistry().getValue(rl == null ? ModSpellParts.MISSING_SHAPE.getId() : rl);
        return part instanceof SpellShape ? (SpellShape) part : null;
    }

    public static SpellModifier getModifierFromName(String shapeName) {
        ResourceLocation rl = ResourceLocation.tryCreate(shapeName);
        AbstractSpellPart part = ArsMagicaAPI.getSpellPartRegistry().getValue(rl == null ? ModSpellParts.MISSING_SHAPE.getId() : rl);
        return part instanceof SpellModifier ? (SpellModifier) part : null;
    }

    public static SpellComponent getComponentFromName(String shapeName) {
        ResourceLocation rl = ResourceLocation.tryCreate(shapeName);
        AbstractSpellPart part = ArsMagicaAPI.getSpellPartRegistry().getValue(rl == null ? ModSpellParts.MISSING_SHAPE.getId() : rl);
        return part instanceof SpellComponent ? (SpellComponent) part : null;
    }
}
