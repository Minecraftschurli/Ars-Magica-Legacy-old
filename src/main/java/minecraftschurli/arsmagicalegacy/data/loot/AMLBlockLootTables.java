package minecraftschurli.arsmagicalegacy.data.loot;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.data.loot.*;
import net.minecraft.item.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLBlockLootTables extends BlockLootTables {

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
}
