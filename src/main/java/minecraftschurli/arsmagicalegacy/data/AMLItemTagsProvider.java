package minecraftschurli.arsmagicalegacy.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLItemTagsProvider extends ItemTagsProvider {
    public AMLItemTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void registerTags() {
        //TODO @Minecraftschurli : Register Item Tags
    }
}
