package minecraftschurli.arsmagicalegacy.data.tags;

import minecraftschurli.arsmagicalegacy.init.ModFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.FluidTags;

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
