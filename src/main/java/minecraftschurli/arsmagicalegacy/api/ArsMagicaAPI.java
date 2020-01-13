package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.MissingShape;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-03
 */
public class ArsMagicaAPI {

    private static IForgeRegistry<SkillTree> SKILL_TREE_REGISTRY = null;
    private static IForgeRegistry<AbstractSpellPart> SPELL_PART_REGISTRY = null;
    private static IForgeRegistry<Skill> SKILL_REGISTRY = null;

    public static final RegistryObject<SpellShape> MISSING_SHAPE = SpellRegistry.registerSpellShape(ArsMagicaLegacy.MODID, "null", null, MissingShape::new, null, 0, 0);

    public static final Supplier<SkillPoint> SILVER_POINT = SkillPointRegistry.registerSkillPoint(-1, new SkillPoint(TextFormatting.GRAY, 0x999999, -1, -1).disableRender());
    public static final Supplier<SkillPoint> SKILL_POINT_1 = SkillPointRegistry.registerSkillPoint(0, new SkillPoint(TextFormatting.BLUE, 0x0000ff, 0, 1));
    public static final Supplier<SkillPoint> SKILL_POINT_2 = SkillPointRegistry.registerSkillPoint(1, new SkillPoint(TextFormatting.GREEN, 0x00ff00, 20, 2));
    public static final Supplier<SkillPoint> SKILL_POINT_3 = SkillPointRegistry.registerSkillPoint(2, new SkillPoint(TextFormatting.RED, 0xff0000, 30, 2));
    //public static final Supplier<SkillPoint> SKILL_POINT_4 = SpellRegistry.registerSkillPoint(new SkillPoint(4, TextFormatting.YELLOW, 0xffff00, 40, 3));
    //public static final Supplier<SkillPoint> SKILL_POINT_5 = SpellRegistry.registerSkillPoint(new SkillPoint(5, TextFormatting.LIGHT_PURPLE, 0xff00ff, 50, 3));
    //public static final Supplier<SkillPoint> SKILL_POINT_6 = SpellRegistry.registerSkillPoint(new SkillPoint(6, TextFormatting.AQUA, 0x00ffff, 60, 4));
    public static final RegistryObject<SkillTree> OFFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "offense", 0);
    public static final RegistryObject<SkillTree> DEFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "defense", 1);
    public static final RegistryObject<SkillTree> UTILITY = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "utility", 2);
    public static final RegistryObject<SkillTree> AFFINITY = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "affinity", 3);
    public static final RegistryObject<SkillTree> TALENT = SkillTreeRegistry.registerSkillTree(ArsMagicaLegacy.MODID, "talent", 4);

    public static void setup() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(SpellRegistry::onSpellPartRegister);
        modEventBus.addListener(SkillRegistry::onSkillRegister);
        modEventBus.addListener(SkillTreeRegistry::onSkillTreeRegister);
        modEventBus.addListener(ArsMagicaAPI::registerRegistries);
        modEventBus.addListener(ArsMagicaAPI::registerItemColorHandler);
    }

    public static void init() {
        IngredientTypes.registerDefault();
    }

    private static void registerItemColorHandler(ColorHandlerEvent.Item event) {
        //noinspection ConstantConditions
        event.getItemColors()
                .register(
                        (stack, tint) -> tint == 0 && stack.hasTag() ? SkillPointRegistry.getSkillPointFromTier(stack.getTag().getInt(InfinityOrbItem.TYPE_KEY)).getColor() : -1,
                        ModItems.INFINITY_ORB.get()
                );
    }

    private static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PART_REGISTRY = new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_parts")).setType(AbstractSpellPart.class).create();
        SKILL_REGISTRY = new RegistryBuilder<Skill>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skills")).setType(Skill.class).create();
        SKILL_TREE_REGISTRY = new RegistryBuilder<SkillTree>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skill_trees")).setType(SkillTree.class).create();

        ModSpellParts.register();
    }


    public static IForgeRegistry<AbstractSpellPart> getSpellPartRegistry() {
        return SPELL_PART_REGISTRY;
    }

    public static IForgeRegistry<Skill> getSkillRegistry() {
        return SKILL_REGISTRY;
    }

    public static IForgeRegistry<SkillTree> getSkillTreeRegistry() {
        return SKILL_TREE_REGISTRY;
    }
}
