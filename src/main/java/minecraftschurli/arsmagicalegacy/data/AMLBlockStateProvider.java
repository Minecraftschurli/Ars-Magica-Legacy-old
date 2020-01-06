package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.*;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2019-12-14
 */
public class AMLBlockStateProvider extends BlockStateProvider {
    public AMLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArsMagicaLegacy.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.ALTAR_CORE);
        airBlock(ModBlocks.ALTAR_VIEW);
        flowerBlock(ModBlocks.AUM);
        flowerBlock(ModBlocks.CERUBLOSSOM);
        simpleBlock(ModBlocks.CHIMERITE_BLOCK);
        simpleBlock(ModBlocks.CHIMERITE_ORE);
        flowerBlock(ModBlocks.DESERT_NOVA);
        /*railFlat(ModBlocks.GOLD_INLAY);
        railFlat(ModBlocks.IRON_INLAY);*/
        simpleBlock(ModBlocks.MAGIC_WALL);
        simpleBlock(ModBlocks.MANA_BATTERY);
        simpleBlock(ModBlocks.MOONSTONE_BLOCK);
        simpleBlock(ModBlocks.MOONSTONE_ORE);
        //railFlat(ModBlocks.REDSTONE_INLAY);
        logBlock(ModBlocks.STRIPPED_WITCHWOOD_LOG);
        barkBlock(ModBlocks.STRIPPED_WITCHWOOD_WOOD, "block/stripped_witchwood_log");
        simpleBlock(ModBlocks.SUNSTONE_BLOCK);
        simpleBlock(ModBlocks.SUNSTONE_ORE);
        flowerBlock(ModBlocks.TARMA_ROOT);
        simpleBlock(ModBlocks.TOPAZ_BLOCK);
        simpleBlock(ModBlocks.TOPAZ_ORE);
        simpleBlock(ModBlocks.VINTEUM_BLOCK);
        simpleBlock(ModBlocks.VINTEUM_ORE);
        flowerBlock(ModBlocks.WAKEBLOOM);
        doorBlock(ModBlocks.WITCHWOOD_DOOR, "witchwood");
        fenceBlock(ModBlocks.WITCHWOOD_FENCE);
        fenceGateBlock(ModBlocks.WITCHWOOD_FENCE_GATE);
        simpleBlock(ModBlocks.WITCHWOOD_LEAVES);
        logBlock(ModBlocks.WITCHWOOD_LOG);
        flowerBlock(ModBlocks.WITCHWOOD_SAPLING);
        slabStairBlock(ModBlocks.WITCHWOOD_PLANKS, ModBlocks.WITCHWOOD_SLAB, ModBlocks.WITCHWOOD_STAIRS);
        trapdoorBlock(ModBlocks.WITCHWOOD_TRAPDOOR);
        barkBlock(ModBlocks.WITCHWOOD_WOOD, "block/witchwood_log");
    }

    private void trapdoorBlock(Supplier<? extends TrapDoorBlock> block) {
        trapdoorBlock(block.get(), blockTexture(block.get()), true);
    }

    protected void slabStairBlock(Supplier<? extends Block> block, Supplier<? extends SlabBlock> slab, Supplier<? extends StairsBlock> stairs) {
        slabStairBlock(block.get(), slab.get(), stairs.get());
    }

    protected void slabStairBlock(Block block, SlabBlock slab, StairsBlock stairs) {
        simpleBlock(block);
        slabBlock(slab, cubeAll(block).getLocation(), blockTexture(block));
        stairsBlock(stairs, blockTexture(block));
    }

    protected void fenceGateBlock(Supplier<? extends FenceGateBlock> block) {
        fenceGateBlock(block.get(), blockTexture(block.get()));
    }

    protected void fenceBlock(Supplier<? extends FenceBlock> block) {
        fenceBlock(block.get(), blockTexture(block.get()));
    }

    protected void doorBlock(Supplier<? extends DoorBlock> block, String name) {
        doorBlock(block.get(), name, modLoc("block/"+name+"_door_bottom"), modLoc("block/"+name+"_door_top"));
    }

    protected void logBlock(Supplier<? extends LogBlock> block) {
        logBlock(block.get());
    }

    protected void barkBlock(Supplier<? extends Block> block, String logTexture) {
        simpleBlock(block.get(), cubeAll(block.get().getRegistryName().getPath(), modLoc(logTexture)));
    }

    protected void simpleBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

    protected void airBlock(Supplier<? extends Block> block) {
        airBlock(block.get());
    }

    protected void airBlock(Block block) {
        simpleBlock(block, getExistingFile(mcLoc("block/air")));
    }

    /*protected void railFlat(Supplier<? extends Block> block) {
        railFlat(block.get());
    }

    protected void railFlat(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH).modelForState().modelFile().addModel();
        simpleBlock(block, singleTexture(block.getRegistryName().getPath(), mcLoc("block/rail_flat"), "rail", blockTexture(block)));
    }*/

    protected void flowerBlock(Supplier<? extends Block> block) {
        flowerBlock(block.get());
    }

    protected void flowerBlock(Block block) {
        simpleBlock(block, cross(block.getRegistryName().getPath(), blockTexture(block)));
    }

    @Nonnull
    @Override
    public String getName() {
        return ArsMagicaLegacy.MODNAME + " Block State Provider";
    }
}
