package minecraftschurli.arsmagicalegacy.data.loot;

import com.google.common.collect.Maps;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public final class AMLBlockLootTables extends BlockLootTables {
    private final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();

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

    public void registerGemDropLootTable(Block block, Item gem) {
        this.registerLootTable(block, droppingItemWithFortune(block, gem));
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
        addTables();
        Set<ResourceLocation> set = new HashSet<>();
        for (Block block : getKnownBlocks()) {
            ResourceLocation resourcelocation = block.getLootTable();
            if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
                LootTable.Builder loottable$builder = this.lootTables.remove(resourcelocation);
                if (loottable$builder == null) {
                    continue;
                }
                p_accept_1_.accept(resourcelocation, loottable$builder);
            }
        }
    }

    protected void registerLootTable(Block blockIn, LootTable.Builder table) {
        this.lootTables.put(blockIn.getLootTable(), table);
    }
}
