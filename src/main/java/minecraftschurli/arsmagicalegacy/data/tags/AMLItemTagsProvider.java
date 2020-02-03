package minecraftschurli.arsmagicalegacy.data.tags;

import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraftforge.common.*;

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
        getBuilder(ModTags.Items.GEMS_CHIMERITE).add(ModItems.CHIMERITE.get());
        getBuilder(ModTags.Items.GEMS_MOONSTONE).add(ModItems.MOONSTONE.get());
        getBuilder(ModTags.Items.GEMS_SUNSTONE).add(ModItems.SUNSTONE.get());
        getBuilder(ModTags.Items.GEMS_TOPAZ).add(ModItems.TOPAZ.get());
        getBuilder(Tags.Items.GEMS).add(
                ModTags.Items.GEMS_CHIMERITE,
                ModTags.Items.GEMS_MOONSTONE,
                ModTags.Items.GEMS_SUNSTONE,
                ModTags.Items.GEMS_TOPAZ
        );

        getBuilder(ModTags.Items.DUSTS_VINTEUM).add(ModItems.VINTEUM.get());
        getBuilder(Tags.Items.DUSTS)
                .add(
                        ModTags.Items.DUSTS_VINTEUM
                ).add(
                        ModItems.PURIFIED_VINTEUM.get(),
                        ModItems.ARCANE_ASH.get()
                );

        getBuilder(ModTags.Items.RUNES).add(
                ModItems.RUNE.get(),
                ModItems.WHITE_RUNE.get(),
                ModItems.ORANGE_RUNE.get(),
                ModItems.MAGENTA_RUNE.get(),
                ModItems.LIGHT_BLUE_RUNE.get(),
                ModItems.YELLOW_RUNE.get(),
                ModItems.LIME_RUNE.get(),
                ModItems.PINK_RUNE.get(),
                ModItems.GRAY_RUNE.get(),
                ModItems.LIGHT_GRAY_RUNE.get(),
                ModItems.CYAN_RUNE.get(),
                ModItems.PURPLE_RUNE.get(),
                ModItems.BLUE_RUNE.get(),
                ModItems.BROWN_RUNE.get(),
                ModItems.GREEN_RUNE.get(),
                ModItems.RED_RUNE.get(),
                ModItems.BLACK_RUNE.get()
        );

        copy(ModTags.Blocks.ORES_CHIMERITE, ModTags.Items.ORES_CHIMERITE);
        copy(ModTags.Blocks.STORAGE_BLOCKS_CHIMERITE, ModTags.Items.STORAGE_BLOCKS_CHIMERITE);
        copy(ModTags.Blocks.ORES_MOONSTONE, ModTags.Items.ORES_MOONSTONE);
        copy(ModTags.Blocks.STORAGE_BLOCKS_MOONSTONE, ModTags.Items.STORAGE_BLOCKS_MOONSTONE);
        copy(ModTags.Blocks.ORES_SUNSTONE, ModTags.Items.ORES_SUNSTONE);
        copy(ModTags.Blocks.STORAGE_BLOCKS_SUNSTONE, ModTags.Items.STORAGE_BLOCKS_SUNSTONE);
        copy(ModTags.Blocks.ORES_TOPAZ, ModTags.Items.ORES_TOPAZ);
        copy(ModTags.Blocks.STORAGE_BLOCKS_TOPAZ, ModTags.Items.STORAGE_BLOCKS_TOPAZ);
        copy(ModTags.Blocks.ORES_VINTEUM, ModTags.Items.ORES_VINTEUM);
        copy(ModTags.Blocks.STORAGE_BLOCKS_VINTEUM, ModTags.Items.STORAGE_BLOCKS_VINTEUM);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        copy(ModTags.Blocks.LOGS_WITCHWOOD, ModTags.Items.LOGS_WITCHWOOD);

        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
    }
}
