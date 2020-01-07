package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-14
 */
public class AMLBlockStateProvider extends BlockStateProvider {
    public AMLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArsMagicaLegacy.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    @Nonnull
    @Override
    public String getName() {
        return ArsMagicaLegacy.MODNAME + " Block State Provider";
    }
}
