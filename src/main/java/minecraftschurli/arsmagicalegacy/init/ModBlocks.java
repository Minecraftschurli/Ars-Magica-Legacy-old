package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.*;
import minecraftschurli.arsmagicalegacy.objects.block.occulus.*;
import minecraftschurli.arsmagicalegacy.objects.tree.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModBlocks implements IInit {
    public static final RegistryObject<Block> CHIMERITE_ORE = BLOCKS.register("chimerite_ore", () -> new OreBase(
            Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE),
            OreBase.OreSpawnProperties.create(0, 0, 0, 0)));
    public static final RegistryObject<Block> CHIMERITE_BLOCK = BLOCKS.register("chimerite_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new OreBase(
            Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE),
            OreBase.OreSpawnProperties.create(0, 0, 0, 0)));
    public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> VINTEUM_ORE = BLOCKS.register("vinteum_ore", () -> new OreBase(
            Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE),
            OreBase.OreSpawnProperties.create(0, 0, 0, 0)));
    public static final RegistryObject<Block> VINTEUM_BLOCK = BLOCKS.register("vinteum_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = BLOCKS.register("moonstone_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> SUNSTONE_ORE = BLOCKS.register("sunstone_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SUNSTONE_BLOCK = BLOCKS.register("sunstone_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> ALTAR_CORE = BLOCKS.register("altar_core", () -> new Block(Block.Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MAGIC_WALL = BLOCKS.register("magic_wall", () -> new IceBlock(Block.Properties.create(Material.ICE).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).slipperiness(0.6f)));

    public static final RegistryObject<Block> WITCHWOOD_LOG = BLOCKS.register("witchwood_log", () -> new LogBlock(MaterialColor.PURPLE, Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> WITCHWOOD_WOOD = BLOCKS.register("witchwood_wood", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> STRIPPED_WITCHWOOD_LOG = BLOCKS.register("stripped_witchwood_log", () -> new LogBlock(MaterialColor.PURPLE, Block.Properties.create(Material.WOOD, MaterialColor.PURPLE).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> STRIPPED_WITCHWOOD_WOOD = BLOCKS.register("stripped_witchwood_wood", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.PURPLE).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> WITCHWOOD_PLANKS = BLOCKS.register("witchwood_planks", () -> new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> WITCHWOOD_LEAVES = BLOCKS.register("witchwood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f)));
    public static final RegistryObject<Block> WITCHWOOD_SLAB = BLOCKS.register("witchwood_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> WITCHWOOD_STAIRS = BLOCKS.register("witchwood_stairs", () -> new StairsBlock(ModBlocks.WITCHWOOD_PLANKS.lazyMap(Block::getDefaultState), Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_FENCE = BLOCKS.register("witchwood_fence", () -> new FenceBlock(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_FENCE_GATE = BLOCKS.register("witchwood_fence_gate", () -> new FenceGateBlock(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_DOOR = BLOCKS.register("witchwood_door", () -> new Door(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_TRAPDOOR = BLOCKS.register("witchwood_trapdoor", () -> new Trapdoor(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_BUTTON = BLOCKS.register("witchwood_button", () -> new Button(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_PRESSURE_PLATE = BLOCKS.register("witchwood_pressure_plate", () -> new PressurePlate(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_SAPLING = BLOCKS.register("witchwood_sapling", () -> new Sapling(new WitchwoodTree(), Block.Properties.create(Material.PLANTS)));

    public static final RegistryObject<FlowerBlock> AUM = BLOCKS.register("aum", () -> new FlowerBlock(Effects.WEAKNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> CERUBLOSSOM = BLOCKS.register("cerublossom", () -> new FlowerBlock(Effects.WEAKNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> DESERT_NOVA = BLOCKS.register("desert_nova", () -> new FlowerBlock(Effects.WEAKNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> TARMA_ROOT = BLOCKS.register("tarma_root", () -> new FlowerBlock(Effects.WEAKNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> WAKEBLOOM = BLOCKS.register("wakebloom", () -> new FlowerBlock(Effects.WEAKNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> REDSTONE_INLAY = BLOCKS.register("redstone_inlay", () -> new Rail(Block.Properties.create(Material.MISCELLANEOUS)));
    public static final RegistryObject<Block> IRON_INLAY = BLOCKS.register("iron_inlay", () -> new Rail(Block.Properties.create(Material.MISCELLANEOUS)));
    public static final RegistryObject<Block> GOLD_INLAY = BLOCKS.register("gold_inlay", () -> new Rail(Block.Properties.create(Material.MISCELLANEOUS)));
    public static final RegistryObject<Block> VINTEUM_TORCH = BLOCKS.register("vinteum_torch", () -> new Torch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(14).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VINTEUM_WALL_TORCH = BLOCKS.register("vinteum_wall_torch", () -> new WallTorch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(14).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OCCULUS = BLOCKS.register("occulus", BlockOcculus::new);

    public static void register() {
    }
}
