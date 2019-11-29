package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLFluidTagsProvider extends FluidTagsProvider {

    public AMLFluidTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        this.getBuilder(FluidTags.WATER).add(ModFluids.LIQUID_ESSENCE.get(), ModFluids.LIQUID_ESSENCE_FLOWING.get());
    }
}
