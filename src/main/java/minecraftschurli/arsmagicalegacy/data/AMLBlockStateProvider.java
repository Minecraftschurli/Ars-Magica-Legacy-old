package minecraftschurli.arsmagicalegacy.data;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

/**
 * @author Minecraftschurli
 * @version 2019-12-14
 */
public class AMLBlockStateProvider extends BlockStateProvider {
    public AMLBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArsMagicaAPI.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        airBlock(ModBlocks.ALTAR_VIEW);
//        doorBlock(ModBlocks.KEYSTONE_DOOR, "keystone");
//        doorBlock(ModBlocks.SPELL_SEALED_DOOR, "spell_sealed");
        doorBlock(ModBlocks.WITCHWOOD_DOOR, "witchwood");
        flowerBlock(ModBlocks.AUM);
        flowerBlock(ModBlocks.CERUBLOSSOM);
        flowerBlock(ModBlocks.DESERT_NOVA);
        flowerBlock(ModBlocks.TARMA_ROOT);
        flowerBlock(ModBlocks.WAKEBLOOM);
        flowerBlock(ModBlocks.WITCHWOOD_SAPLING);
        logBlock(ModBlocks.STRIPPED_WITCHWOOD_LOG);
        logBlock(ModBlocks.WITCHWOOD_LOG);
        railBlock(ModBlocks.GOLD_INLAY);
        railBlock(ModBlocks.IRON_INLAY);
        railBlock(ModBlocks.REDSTONE_INLAY);
        simpleBlock(ModBlocks.ALTAR_CORE);
        simpleBlock(ModBlocks.CHIMERITE_BLOCK);
        simpleBlock(ModBlocks.CHIMERITE_ORE);
        simpleBlock(ModBlocks.EVERSTONE);
        simpleBlock(ModBlocks.FLICKER_LURE);
        simpleBlock(ModBlocks.MAGIC_WALL);
        simpleBlock(ModBlocks.MANA_BATTERY);
        simpleBlock(ModBlocks.MOONSTONE_BLOCK);
        simpleBlock(ModBlocks.MOONSTONE_ORE);
        simpleBlock(ModBlocks.SLIPSTREAM_GENERATOR);
        simpleBlock(ModBlocks.STRIPPED_WITCHWOOD_WOOD);
        simpleBlock(ModBlocks.SUNSTONE_BLOCK);
        simpleBlock(ModBlocks.SUNSTONE_ORE);
        simpleBlock(ModBlocks.TOPAZ_BLOCK);
        simpleBlock(ModBlocks.TOPAZ_ORE);
        simpleBlock(ModBlocks.VINTEUM_BLOCK);
        simpleBlock(ModBlocks.VINTEUM_ORE);
        simpleBlock(ModBlocks.WITCHWOOD_LEAVES);
        simpleBlock(ModBlocks.WITCHWOOD_WOOD);
        slabStairFenceBlock(ModBlocks.WITCHWOOD_PLANKS, ModBlocks.WITCHWOOD_SLAB, ModBlocks.WITCHWOOD_STAIRS, ModBlocks.WITCHWOOD_FENCE, ModBlocks.WITCHWOOD_FENCE_GATE);
        trapdoorBlock(ModBlocks.KEYSTONE_TRAPDOOR);
        trapdoorBlock(ModBlocks.WITCHWOOD_TRAPDOOR);
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
        simpleBlock(block, models().getExistingFile(mcLoc("block/air")));
    }

    protected void railBlock(Supplier<? extends Block> block) {
        railBlock(block.get());
    }

    protected void railBlock(Block block) {
        if (!(block instanceof AbstractRailBlock))
            return;
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        ResourceLocation blockTex = blockTexture(block);
        ModelFile straight = models().withExistingParent(block.getRegistryName().getPath(), mcLoc("block/rail")).texture("rail", blockTex);
        ModelFile curved = models().withExistingParent(block.getRegistryName().getPath() + "_corner", mcLoc("block/rail_curved")).texture("rail", new ResourceLocation(blockTex.getNamespace(), blockTex.getPath()+"_corner"));
        ModelFile raisedNE = models().withExistingParent(block.getRegistryName().getPath() + "_raised_ne", mcLoc("block/template_rail_raised_ne")).texture("rail", blockTex);
        ModelFile raisedSW = models().withExistingParent(block.getRegistryName().getPath() + "_raised_sw", mcLoc("block/template_rail_raised_sw")).texture("rail", blockTex);
        builder.forAllStates(state -> {
            switch (state.get(((AbstractRailBlock) block).getShapeProperty())) {
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
        simpleBlock(block, models().cross(block.getRegistryName().getPath(), blockTexture(block)));
    }

    @Nonnull
    @Override
    public String getName() {
        return ArsMagicaAPI.MODNAME + " Block State Provider";
    }
}
