package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.data.ArsMagicaLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;

/**
 * @author Minecraftschurli
 * @version 2020-01-06
 */
public class AMLGermanLanguageProvider extends ArsMagicaLanguageProvider {
    public AMLGermanLanguageProvider(DataGenerator gen) {
        super(gen, ArsMagicaLegacy.MODID, "de_de");
    }

    @Override
    protected void addTranslations() {

    }
}
