package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * @author Minecraftschurli
 * @version 2019-11-15
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaLegacy.MODID)
public class ModRegistries {
    public static IForgeRegistry<SpellComponent> SPELL_COMPONENTS = null;
    public static IForgeRegistry<SpellModifier> SPELL_MODIFIERS = null;
    public static IForgeRegistry<SpellShape> SPELL_SHAPES = null;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_COMPONENTS = new RegistryBuilder<SpellComponent>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_components")).setType(SpellComponent.class).create();
        SPELL_MODIFIERS = new RegistryBuilder<SpellModifier>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_modifiers")).setType(SpellModifier.class).create();
        SPELL_SHAPES = new RegistryBuilder<SpellShape>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_shapes")).setType(SpellShape.class).create();

        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEffects.register();
        ModContainers.register();
        SpellParts.register();
    }
}
