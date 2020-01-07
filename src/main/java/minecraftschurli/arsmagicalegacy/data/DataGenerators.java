package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaLegacy.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            gen.addProvider(new AMLRecipeProvider(gen));
            gen.addProvider(new AMLBlockLootTableProvider(gen));
            gen.addProvider(new AMLItemTagsProvider(gen));
            gen.addProvider(new AMLBlockTagsProvider(gen));
            gen.addProvider(new AMLFluidTagsProvider(gen));
        }
        if (event.includeClient()) {
            gen.addProvider(new AMLEnglishLanguageProvider(gen));
            gen.addProvider(new AMLGermanLanguageProvider(gen));
            gen.addProvider(new AMLBlockStateProvider(gen, helper));
            gen.addProvider(new AMLItemModelProvider(gen, helper));
        }
    }
}
