package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.spellsystem.SpellPart;
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
    public static IForgeRegistry<SpellPart> SPELL_PARTS = null;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PARTS = new RegistryBuilder<SpellPart>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_parts")).setType(SpellPart.class).create();

        IInit.setEventBus(FMLJavaModLoadingContext.get().getModEventBus());

        ModBlocks.register();
        ModFluids.register();
        ModItems.register();
        ModEffects.register();
        ModContainers.register();
        SpellParts.register();
    }
}
