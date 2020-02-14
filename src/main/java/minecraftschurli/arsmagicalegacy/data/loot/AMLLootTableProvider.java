package minecraftschurli.arsmagicalegacy.data.loot;

import com.google.common.collect.*;
import com.mojang.datafixers.util.*;
import net.minecraft.data.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-15
 */
public class AMLLootTableProvider extends LootTableProvider {
    public AMLLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(new Pair<>(AMLBlockLootTables::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        //super.validate(map, validationtracker);
    }
}
