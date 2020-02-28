package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-04
 */
public class AMLItemModelProvider extends ItemModelProvider {
    public AMLItemModelProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, ArsMagicaAPI.MODID, helper);
    }

    @Override
    protected void registerModels() {

    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return ArsMagicaAPI.MODNAME + " Item Model Provider";
    }
}
