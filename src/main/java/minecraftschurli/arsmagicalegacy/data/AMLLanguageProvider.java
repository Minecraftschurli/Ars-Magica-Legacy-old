package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * @author Minecraftschurli
 * @version 2020-01-01
 */
public class AMLLanguageProvider extends LanguageProvider {
    public AMLLanguageProvider(DataGenerator gen) {
        super(gen, ArsMagicaLegacy.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.SUNSTONE_BLOCK, "Sunstone Block");
        addBlock(ModBlocks.WITCHWOOD_PLANKS, "Withwood Planks");
        addBlock(ModBlocks.MOONSTONE_ORE, "Moonstone Ore");
        addBlock(ModBlocks.ALTAR_CORE, "Crafting Altar Core");
    }
}
