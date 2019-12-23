package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraftforge.common.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class AMLBlockTagsProvider extends BlockTagsProvider {
    public AMLBlockTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        getBuilder(ModTags.Blocks.ORES_CHIMERITE).add(ModBlocks.CHIMERITE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_SUNSTONE).add(ModBlocks.SUNSTONE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_MOONSTONE).add(ModBlocks.MOONSTONE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_TOPAZ).add(ModBlocks.TOPAZ_ORE.get());
        getBuilder(ModTags.Blocks.ORES_VINTEUM).add(ModBlocks.VINTEUM_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(
                ModTags.Blocks.ORES_CHIMERITE,
                ModTags.Blocks.ORES_SUNSTONE,
                ModTags.Blocks.ORES_MOONSTONE,
                ModTags.Blocks.ORES_TOPAZ,
                ModTags.Blocks.ORES_VINTEUM
        );

        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_CHIMERITE).add(ModBlocks.CHIMERITE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_SUNSTONE).add(ModBlocks.SUNSTONE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_MOONSTONE).add(ModBlocks.MOONSTONE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(ModBlocks.TOPAZ_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_VINTEUM).add(ModBlocks.VINTEUM_BLOCK.get());
        getBuilder(Tags.Blocks.STORAGE_BLOCKS).add(
                ModTags.Blocks.STORAGE_BLOCKS_CHIMERITE,
                ModTags.Blocks.STORAGE_BLOCKS_SUNSTONE,
                ModTags.Blocks.STORAGE_BLOCKS_MOONSTONE,
                ModTags.Blocks.STORAGE_BLOCKS_TOPAZ,
                ModTags.Blocks.STORAGE_BLOCKS_VINTEUM
        );

        getBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.WITCHWOOD_DOOR.get());
        getBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.WITCHWOOD_TRAPDOOR.get());
        getBuilder(Tags.Blocks.FENCES_WOODEN).add(ModBlocks.WITCHWOOD_FENCE.get());
        getBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.WITCHWOOD_FENCE_GATE.get());
        getBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.WITCHWOOD_BUTTON.get());
        getBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.WITCHWOOD_PRESSURE_PLATE.get());
        getBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.WITCHWOOD_SLAB.get());
        getBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.WITCHWOOD_STAIRS.get());
        getBuilder(BlockTags.PLANKS).add(ModBlocks.WITCHWOOD_PLANKS.get());
        getBuilder(ModTags.Blocks.LOGS_WITCHWOOD).add(
                ModBlocks.WITCHWOOD_LOG.get(),
                ModBlocks.WITCHWOOD_WOOD.get(),
                ModBlocks.STRIPPED_WITCHWOOD_LOG.get(),
                ModBlocks.STRIPPED_WITCHWOOD_WOOD.get()
        );
        getBuilder(BlockTags.LOGS).add(ModTags.Blocks.LOGS_WITCHWOOD);
    }
}
