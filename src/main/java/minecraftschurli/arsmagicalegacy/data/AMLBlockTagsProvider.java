package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.init.Blocks;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

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
        //TODO @Minecraftschurli : Register Block Tags
        getBuilder(ModTags.Blocks.ORES_CHIMERITE).add(Blocks.CHIMERITE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_SUNSTONE).add(Blocks.SUNSTONE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_MOONSTONE).add(Blocks.MOONSTONE_ORE.get());
        getBuilder(ModTags.Blocks.ORES_TOPAZ).add(Blocks.TOPAZ_ORE.get());
        getBuilder(ModTags.Blocks.ORES_VINTEUM).add(Blocks.VINTEUM_ORE.get());
        getBuilder(Tags.Blocks.ORES).add(
                ModTags.Blocks.ORES_CHIMERITE,
                ModTags.Blocks.ORES_SUNSTONE,
                ModTags.Blocks.ORES_MOONSTONE,
                ModTags.Blocks.ORES_TOPAZ,
                ModTags.Blocks.ORES_VINTEUM
        );

        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_CHIMERITE).add(Blocks.CHIMERITE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_SUNSTONE).add(Blocks.SUNSTONE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_MOONSTONE).add(Blocks.MOONSTONE_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(Blocks.TOPAZ_BLOCK.get());
        getBuilder(ModTags.Blocks.STORAGE_BLOCKS_VINTEUM).add(Blocks.VINTEUM_BLOCK.get());
        getBuilder(Tags.Blocks.STORAGE_BLOCKS).add(
                ModTags.Blocks.STORAGE_BLOCKS_CHIMERITE,
                ModTags.Blocks.STORAGE_BLOCKS_SUNSTONE,
                ModTags.Blocks.STORAGE_BLOCKS_MOONSTONE,
                ModTags.Blocks.STORAGE_BLOCKS_TOPAZ,
                ModTags.Blocks.STORAGE_BLOCKS_VINTEUM
        );

        getBuilder(BlockTags.WOODEN_DOORS).add(Blocks.WITCHWOOD_DOOR.get());
        getBuilder(BlockTags.WOODEN_TRAPDOORS).add(Blocks.WITCHWOOD_TRAPDOOR.get());
        getBuilder(Tags.Blocks.FENCES_WOODEN).add(Blocks.WITCHWOOD_FENCE.get());
        getBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(Blocks.WITCHWOOD_FENCE_GATE.get());
        getBuilder(BlockTags.WOODEN_BUTTONS).add(Blocks.WITCHWOOD_BUTTON.get());
        getBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(Blocks.WITCHWOOD_PRESSURE_PLATE.get());
        getBuilder(BlockTags.WOODEN_SLABS).add(Blocks.WITCHWOOD_SLAB.get());
        getBuilder(BlockTags.WOODEN_STAIRS).add(Blocks.WITCHWOOD_STAIRS.get());
        getBuilder(ModTags.Blocks.WITCHWOOD_LOGS).add(
                Blocks.WITCHWOOD_LOG.get()
        );
        getBuilder(BlockTags.LOGS).add(ModTags.Blocks.WITCHWOOD_LOGS);
    }
}
