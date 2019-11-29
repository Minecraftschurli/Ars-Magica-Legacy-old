package minecraftschurli.arsmagicalegacy.data;

import com.google.gson.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.data.*;
import net.minecraft.data.loot.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLBlockLootTableProvider extends BlockLootTables implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private final DataGenerator generator;

    public AMLBlockLootTableProvider(DataGenerator dataGeneratorIn) {
        this.generator = dataGeneratorIn;
    }

    protected void addTables() {
        registerDropSelfLootTable(ModBlocks.CHIMERITE_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.MOONSTONE_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_LOG.get());
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_PLANKS.get());
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_SLAB.get());
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_STAIRS.get());
        registerDropSelfLootTable(ModBlocks.VINTEUM_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.SUNSTONE_BLOCK.get());
        registerGemDropLootTable(ModBlocks.CHIMERITE_ORE.get(), ModItems.CHIMERITE.get());
        registerGemDropLootTable(ModBlocks.TOPAZ_ORE.get(), ModItems.TOPAZ.get());
        registerGemDropLootTable(ModBlocks.VINTEUM_ORE.get(), ModItems.VINTEUM.get());
        registerGemDropLootTable(ModBlocks.MOONSTONE_ORE.get(), ModItems.MOONSTONE.get());
        registerGemDropLootTable(ModBlocks.SUNSTONE_ORE.get(), ModItems.SUNSTONE.get());
    }

    public void addTable(Block block, LootTable.Builder lootTable) {
        lootTables.put(block, lootTable);
    }

    @Override
    public void act(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return "ArsMagicaLegacyBlockLootTables";
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                ArsMagicaLegacy.LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    public void registerGemDropLootTable(Block block, Item gem) {
        this.registerLootTable(block, droppingItemWithFortune(block, gem));
    }

    @Override
    public void registerLootTable(Block p_218507_1_, LootTable.Builder p_218507_2_) {
        this.addTable(p_218507_1_, p_218507_2_);
    }
}
