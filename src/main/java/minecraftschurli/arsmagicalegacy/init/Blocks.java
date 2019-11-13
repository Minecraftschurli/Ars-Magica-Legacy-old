package minecraftschurli.arsmagicalegacy.init;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class Blocks implements Registries {
    public static final RegistryObject<Block> VINTEUM_ORE = BLOCKS.register("vinteum_ore", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> CHIMERITE_ORE = BLOCKS.register("chimerite_ore", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SUNSTONE_ORE = BLOCKS.register("sunstone_ore", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> VINTEUM_BLOCK = BLOCKS.register("vinteum_block", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> CHIMERITE_BLOCK = BLOCKS.register("chimerite_block", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = BLOCKS.register("moonstone_block", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SUNSTONE_BLOCK = BLOCKS.register("sunstone_block", ()->new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> WITCHWOOD_LOG = BLOCKS.register("witchwood_log", ()->new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> WITCHWOOD_PLANKS = BLOCKS.register("witchwood_planks", ()->new RotatedPillarBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> WITCHWOOD_LEAVES = BLOCKS.register("witchwood_leaves", ()->new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f)));
    public static final RegistryObject<Block> WITCHWOOD_SLAB = BLOCKS.register("witchwood_slab", ()->new SlabBlock(Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> WITCHWOOD_STAIRS = BLOCKS.register("witchwood_stairs", ()->new StairsBlock(WITCHWOOD_PLANKS.lazyMap(Block::getDefaultState), Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_FENCE = BLOCKS.register("witchwood_fence", ()->new FenceBlock(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_FENCE_GATE = BLOCKS.register("witchwood_fence_gate", ()->new FenceGateBlock(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_DOOR = BLOCKS.register("witchwood_door", ()->new Door(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_TRAPDOOR = BLOCKS.register("witchwood_trapdoor", ()->new Trapdoor(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_BUTTON = BLOCKS.register("witchwood_button", ()->new Button(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> WITCHWOOD_PRESSURE_PLATE = BLOCKS.register("witchwood_pressure_plate", ()->new PressurePlate(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Block> ALTAR_CORE = BLOCKS.register("altar_core", ()->new Block(Block.Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MAGIC_WALL = BLOCKS.register("magic_wall", ()->new Block(Block.Properties.create(Material.ICE).harvestLevel(0).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE)));
    public static void register() {}
    //Required because DoorBlock(Block.Properties builder) has protected access
    private static class Door extends DoorBlock {
        public Door(Block.Properties builder) {
            super(builder);
        }
    }
    //Required because TrapDoorBlock(Block.Properties builder) has protected access
    private static class Trapdoor extends TrapDoorBlock {
        public Trapdoor(Block.Properties builder) {
            super(builder);
        }
    }
    //Required because WoodButtonBlock(Block.Properties builder) has protected access
    private static class Button extends WoodButtonBlock {
        public Button(Block.Properties builder) {
            super(builder);
        }
    }
    //Required because PressurePlateBlock(Block.Properties builder) has protected access
    private static class PressurePlate extends PressurePlateBlock {
        public PressurePlate(Block.Properties builder) {
            super(Sensitivity.EVERYTHING, builder);
        }
    }
}
