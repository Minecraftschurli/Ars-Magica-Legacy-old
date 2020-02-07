package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.data.lang.AMLEnglishLanguageProvider;
import minecraftschurli.arsmagicalegacy.data.lang.AMLGermanLanguageProvider;
import minecraftschurli.arsmagicalegacy.data.loot.AMLLootTableProvider;
import minecraftschurli.arsmagicalegacy.data.tags.AMLBlockTagsProvider;
import minecraftschurli.arsmagicalegacy.data.tags.AMLFluidTagsProvider;
import minecraftschurli.arsmagicalegacy.data.tags.AMLItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

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
            gen.addProvider(new AMLLootTableProvider(gen));
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
