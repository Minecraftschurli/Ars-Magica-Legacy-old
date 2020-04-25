package minecraftschurli.arsmagicalegacy.data.loot;

import com.google.common.collect.Maps;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableBlock;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Item;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public final class AMLBlockLootTables extends BlockLootTables {
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();

    protected void addTables() {
        registerDropSelfLootTable(ModBlocks.CHIMERITE_BLOCK);
        registerDropSelfLootTable(ModBlocks.MOONSTONE_BLOCK);
        registerDropSelfLootTable(ModBlocks.VINTEUM_BLOCK);
        registerDropSelfLootTable(ModBlocks.SUNSTONE_BLOCK);
        registerDropSelfLootTable(ModBlocks.TOPAZ_BLOCK);

        registerDropSelfLootTable(ModBlocks.AUM);
        registerDropSelfLootTable(ModBlocks.WAKEBLOOM);
        registerDropSelfLootTable(ModBlocks.TARMA_ROOT);
        registerDropSelfLootTable(ModBlocks.DESERT_NOVA);
        registerDropSelfLootTable(ModBlocks.CERUBLOSSOM);

        registerDropSelfLootTable(ModBlocks.WITCHWOOD_LOG);
        registerDropSelfLootTable(ModBlocks.STRIPPED_WITCHWOOD_LOG);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_WOOD);
        registerDropSelfLootTable(ModBlocks.STRIPPED_WITCHWOOD_WOOD);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_PLANKS);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_SLAB);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_STAIRS);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_SAPLING);
        registerLootTable(ModBlocks.WITCHWOOD_DOOR, block -> droppingWhen(block, DoorBlock.HALF, DoubleBlockHalf.LOWER));
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_TRAPDOOR);
        registerLootTable(ModBlocks.WITCHWOOD_LEAVES, block -> droppingWithChancesAndSticks(block, ModBlocks.WITCHWOOD_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_FENCE);
        registerDropSelfLootTable(ModBlocks.WITCHWOOD_FENCE_GATE);

        registerGemDropLootTable(ModBlocks.CHIMERITE_ORE, ModItems.CHIMERITE);
        registerGemDropLootTable(ModBlocks.TOPAZ_ORE, ModItems.TOPAZ);
        registerGemDropLootTable(ModBlocks.VINTEUM_ORE, ModItems.VINTEUM);
        registerGemDropLootTable(ModBlocks.MOONSTONE_ORE, ModItems.MOONSTONE);
        registerGemDropLootTable(ModBlocks.SUNSTONE_ORE, ModItems.SUNSTONE);

        registerDropSelfLootTable(ModBlocks.ALTAR_CORE);
        registerLootTable(ModBlocks.INSCRIPTION_TABLE, block -> droppingWhen(block, InscriptionTableBlock.HALF, InscriptionTableBlock.Half.RIGHT));
        registerDropSelfLootTable(ModBlocks.OCCULUS);
        registerLootTable(ModBlocks.OBELISK, block -> droppingWhen(block, ObeliskBlock.HALF, DoubleBlockHalf.LOWER));
    }

    private void registerGemDropLootTable(Supplier<? extends Block> block, Supplier<? extends Item> item) {
        registerGemDropLootTable(block.get(), item.get());
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

    public void registerDropSelfLootTable(Supplier<? extends Block> p_218492_1_) {
        registerDropSelfLootTable(p_218492_1_.get());
    }

    protected void registerLootTable(Supplier<? extends Block> blockIn, Function<Block, LootTable.Builder> factory) {
        registerLootTable(blockIn.get(), factory);
    }

    @Override
    protected void registerLootTable(Block blockIn, LootTable.Builder table) {
        this.lootTables.put(blockIn.getLootTable(), table);
    }
}
