package minecraftschurli.arsmagicalegacy.data;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.data.*;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.ResourceLocation;
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
        railBlock(ModBlocks.GOLD_INLAY);
        railBlock(ModBlocks.IRON_INLAY);
        simpleBlock(ModBlocks.MAGIC_WALL);
        simpleBlock(ModBlocks.MANA_BATTERY);
        simpleBlock(ModBlocks.MOONSTONE_BLOCK);
        simpleBlock(ModBlocks.MOONSTONE_ORE);
        railBlock(ModBlocks.REDSTONE_INLAY);
        logBlock(ModBlocks.STRIPPED_WITCHWOOD_LOG);
        simpleBlock(ModBlocks.STRIPPED_WITCHWOOD_WOOD);
        simpleBlock(ModBlocks.SUNSTONE_BLOCK);
        simpleBlock(ModBlocks.SUNSTONE_ORE);
        flowerBlock(ModBlocks.TARMA_ROOT);
        simpleBlock(ModBlocks.TOPAZ_BLOCK);
        simpleBlock(ModBlocks.TOPAZ_ORE);
        simpleBlock(ModBlocks.VINTEUM_BLOCK);
        simpleBlock(ModBlocks.VINTEUM_ORE);
        flowerBlock(ModBlocks.WAKEBLOOM);
        doorBlock(ModBlocks.WITCHWOOD_DOOR, "witchwood");
        simpleBlock(ModBlocks.WITCHWOOD_LEAVES);
        logBlock(ModBlocks.WITCHWOOD_LOG);
        flowerBlock(ModBlocks.WITCHWOOD_SAPLING);
        slabStairFenceBlock(ModBlocks.WITCHWOOD_PLANKS, ModBlocks.WITCHWOOD_SLAB, ModBlocks.WITCHWOOD_STAIRS, ModBlocks.WITCHWOOD_FENCE, ModBlocks.WITCHWOOD_FENCE_GATE);
        trapdoorBlock(ModBlocks.WITCHWOOD_TRAPDOOR);
        simpleBlock(ModBlocks.WITCHWOOD_WOOD);
    }

    private void trapdoorBlock(Supplier<? extends TrapDoorBlock> block) {
        trapdoorBlock(block.get(), blockTexture(block.get()), true);
    }

    protected void slabStairFenceBlock(Supplier<? extends Block> block, Supplier<? extends SlabBlock> slab, Supplier<? extends StairsBlock> stairs, Supplier<? extends FenceBlock> fence, Supplier<? extends FenceGateBlock> gate) {
        slabStairFenceBlock(block.get(), slab.get(), stairs.get(), fence.get(), gate.get());
    }

    protected void slabStairFenceBlock(Block block, SlabBlock slab, StairsBlock stairs, FenceBlock fence, FenceGateBlock gate) {
        simpleBlock(block);
        slabBlock(slab, cubeAll(block).getLocation(), blockTexture(block));
        stairsBlock(stairs, blockTexture(block));
        fenceBlock(fence, blockTexture(block));
        fenceGateBlock(gate, blockTexture(block));
    }

    protected void doorBlock(Supplier<? extends DoorBlock> block, String name) {
        doorBlock(block.get(), name, modLoc("block/"+name+"_door_bottom"), modLoc("block/"+name+"_door_top"));
    }

    protected void logBlock(Supplier<? extends LogBlock> block) {
        logBlock(block.get());
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

    protected void railBlock(Supplier<? extends Block> block) {
        railBlock(block.get());
    }

    protected void railBlock(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        ModelFile straight = withExistingParent(block.getRegistryName().getPath(), mcLoc("block/rail")).texture("rail", blockTexture(block));
        ModelFile curved = withExistingParent(block.getRegistryName().getPath() + "_corner", mcLoc("block/rail_curved")).texture("rail", blockTexture(block));
        ModelFile raisedNE = withExistingParent(block.getRegistryName().getPath() + "_raised_ne", mcLoc("block/template_rail_raised_ne")).texture("rail", blockTexture(block));
        ModelFile raisedSW = withExistingParent(block.getRegistryName().getPath() + "_raised_sw", mcLoc("block/template_rail_raised_sw")).texture("rail", blockTexture(block));
        builder.forAllStates(state -> {
            switch (state.get(RailBlock.SHAPE)) {
                case NORTH_SOUTH:
                    return ConfiguredModel.builder().modelFile(straight).build();
                case EAST_WEST:
                    return ConfiguredModel.builder().modelFile(straight).rotationY(90).build();
                case SOUTH_EAST:
                    return ConfiguredModel.builder().modelFile(curved).build();
                case SOUTH_WEST:
                    return ConfiguredModel.builder().modelFile(curved).rotationY(90).build();
                case NORTH_WEST:
                    return ConfiguredModel.builder().modelFile(curved).rotationY(180).build();
                case NORTH_EAST:
                    return ConfiguredModel.builder().modelFile(curved).rotationY(270).build();
                case ASCENDING_NORTH:
                    return ConfiguredModel.builder().modelFile(raisedNE).build();
                case ASCENDING_EAST:
                    return ConfiguredModel.builder().modelFile(raisedNE).rotationY(90).build();
                case ASCENDING_SOUTH:
                    return ConfiguredModel.builder().modelFile(raisedSW).build();
                case ASCENDING_WEST:
                    return ConfiguredModel.builder().modelFile(raisedSW).rotationY(90).build();
                default:
                    return null;
            }
        });
    }

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
