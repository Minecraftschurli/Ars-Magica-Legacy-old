package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.data.DataGenerator;
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
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new AMLItemTagsProvider(generator));
            generator.addProvider(new AMLBlockTagsProvider(generator));
            generator.addProvider(new AMLFluidTagsProvider(generator));
            generator.addProvider(new AMLRecipeProvider(generator));
            generator.addProvider(new AMLBlockLootTableProvider(generator));
        }
    }
}
