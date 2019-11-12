package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.Fluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.IDataProvider;
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
        this.getBuilder(FluidTags.WATER).add(Fluids.LIQUID_ESSENCE.get(), Fluids.LIQUID_ESSENCE_FLOWING.get());
    }
}
