package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.*;
import minecraftschurli.arsmagicalegacy.objects.block.blackaurem.BlackAuremBlock;
import minecraftschurli.arsmagicalegacy.objects.block.celestialprism.CelestialPrismBlock;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarBlock;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewBlock;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableBlock;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskBlock;
import minecraftschurli.arsmagicalegacy.objects.block.occulus.OcculusBlock;
import minecraftschurli.arsmagicalegacy.worldgen.features.WitchwoodTree;
import minecraftschurli.simpleorelib.Ore;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModBlocks implements IInit {
    //region resources
    public static final RegistryObject<Ore> CHIMERITE_ORE = BLOCKS.register("chimerite_ore", () -> new Ore(
            Block.Properties.create(Material.ROCK).harvestLevel(ItemTier.IRON.getHarvestLevel()).hardnessAndResistance(3, 5).harvestTool(ToolType.PICKAXE),
            Ore.OreSpawnProperties.create(8, 6, 10, 80)));
    public static final RegistryObject<Block> CHIMERITE_BLOCK = BLOCKS.register("chimerite_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5, 6).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Ore> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Ore(
            Block.Properties.create(Material.ROCK).harvestLevel(ItemTier.IRON.getHarvestLevel()).hardnessAndResistance(3, 5).harvestTool(ToolType.PICKAXE),
            Ore.OreSpawnProperties.create(8, 6, 10, 80)));
    public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5, 6).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Ore> VINTEUM_ORE = BLOCKS.register("vinteum_ore", () -> new Ore(
            Block.Properties.create(Material.ROCK).harvestLevel(ItemTier.IRON.getHarvestLevel()).hardnessAndResistance(3, 5).harvestTool(ToolType.PICKAXE),
            Ore.OreSpawnProperties.create(6, 4, 10, 45)));
    public static final RegistryObject<Block> VINTEUM_BLOCK = BLOCKS.register("vinteum_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5, 6).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3, 3).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = BLOCKS.register("moonstone_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5, 6).harvestTool(ToolType.PICKAXE)));

    public static final RegistryObject<Block> SUNSTONE_ORE = BLOCKS.register("sunstone_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).hardnessAndResistance(3, 3).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SUNSTONE_BLOCK = BLOCKS.register("sunstone_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).hardnessAndResistance(5, 6).harvestTool(ToolType.PICKAXE)));
    //endregion
    //region witchwood
    public static final RegistryObject<Block> WITCHWOOD_PLANKS = BLOCKS.register("witchwood_planks", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.PURPLE).sound(SoundType.WOOD).hardnessAndResistance(2, 3).harvestTool(ToolType.AXE)));
    public static final RegistryObject<LogBlock> WITCHWOOD_LOG = BLOCKS.register("witchwood_log", () -> new LogBlock(MaterialColor.PURPLE, Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<RotatedPillarBlock> WITCHWOOD_WOOD = BLOCKS.register("witchwood_wood", () -> new RotatedPillarBlock(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<LogBlock> STRIPPED_WITCHWOOD_LOG = BLOCKS.register("stripped_witchwood_log", () -> new LogBlock(MaterialColor.PURPLE, Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WITCHWOOD_WOOD = BLOCKS.register("stripped_witchwood_wood", () -> new RotatedPillarBlock(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<LeavesBlock> WITCHWOOD_LEAVES = BLOCKS.register("witchwood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).sound(SoundType.PLANT)));
    public static final RegistryObject<SlabBlock> WITCHWOOD_SLAB = BLOCKS.register("witchwood_slab", () -> new SlabBlock(Block.Properties.from(WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<StairsBlock> WITCHWOOD_STAIRS = BLOCKS.register("witchwood_stairs", () -> new StairsBlock(ModBlocks.WITCHWOOD_PLANKS.lazyMap(Block::getDefaultState), Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<FenceBlock> WITCHWOOD_FENCE = BLOCKS.register("witchwood_fence", () -> new FenceBlock(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<FenceGateBlock> WITCHWOOD_FENCE_GATE = BLOCKS.register("witchwood_fence_gate", () -> new FenceGateBlock(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Door> WITCHWOOD_DOOR = BLOCKS.register("witchwood_door", () -> new Door(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get()).noDrops().notSolid()));
    public static final RegistryObject<Trapdoor> WITCHWOOD_TRAPDOOR = BLOCKS.register("witchwood_trapdoor", () -> new Trapdoor(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get()).notSolid()));
    public static final RegistryObject<Button> WITCHWOOD_BUTTON = BLOCKS.register("witchwood_button", () -> new Button(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<PressurePlate> WITCHWOOD_PRESSURE_PLATE = BLOCKS.register("witchwood_pressure_plate", () -> new PressurePlate(Block.Properties.from(ModBlocks.WITCHWOOD_PLANKS.get())));
    public static final RegistryObject<Sapling> WITCHWOOD_SAPLING = BLOCKS.register("witchwood_sapling", () -> new Sapling(new WitchwoodTree()));
    //endregion
    public static final RegistryObject<FlowerBlock> AUM = BLOCKS.register("aum", () -> new FlowerBlock(ModEffects.mana_regen_effect, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> CERUBLOSSOM = BLOCKS.register("cerublossom", () -> new FlowerBlock(Effects.LEVITATION, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> DESERT_NOVA = BLOCKS.register("desert_nova", () -> new FlowerBlock(Effects.FIRE_RESISTANCE, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)) {
        @Override
        protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return Tags.Blocks.SAND.contains(state.getBlock());
        }
    });
    public static final RegistryObject<FlowerBlock> TARMA_ROOT = BLOCKS.register("tarma_root", () -> new FlowerBlock(Effects.SLOWNESS, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)));
    public static final RegistryObject<FlowerBlock> WAKEBLOOM = BLOCKS.register("wakebloom", () -> new FlowerBlock(ModEffects.burnout_reduction_effect, 7, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)));

    public static final RegistryObject<Inlay> REDSTONE_INLAY = BLOCKS.register("redstone_inlay", () -> new Inlay(Block.Properties.create(Material.MISCELLANEOUS).notSolid()));
    public static final RegistryObject<Inlay> IRON_INLAY = BLOCKS.register("iron_inlay", () -> new Inlay(Block.Properties.create(Material.MISCELLANEOUS).notSolid()));
    public static final RegistryObject<Inlay> GOLD_INLAY = BLOCKS.register("gold_inlay", () -> new Inlay(Block.Properties.create(Material.MISCELLANEOUS).notSolid()));

    public static final RegistryObject<Torch> VINTEUM_TORCH = BLOCKS.register("vinteum_torch", () -> new Torch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0).lightValue(14).sound(SoundType.WOOD)));
    public static final RegistryObject<WallTorch> VINTEUM_WALL_TORCH = BLOCKS.register("vinteum_wall_torch", () -> new WallTorch(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0).lightValue(14).sound(SoundType.WOOD)));

    public static final RegistryObject<ObeliskBlock> OBELISK = BLOCKS.register("obelisk", ObeliskBlock::new);
    public static final RegistryObject<CelestialPrismBlock> CELESTIAL_PRISM = BLOCKS.register("celestial_prism", CelestialPrismBlock::new);
    public static final RegistryObject<BlackAuremBlock> BLACK_AUREM = BLOCKS.register("black_aurem", BlackAuremBlock::new);
    public static final RegistryObject<WizardChalk> WIZARD_CHALK = BLOCKS.register("wizard_chalk", () -> new WizardChalk(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0).notSolid().doesNotBlockMovement()));

    public static final RegistryObject<CraftingAltarBlock> ALTAR_CORE = BLOCKS.register("altar_core", CraftingAltarBlock::new);
    public static final RegistryObject<CraftingAltarViewBlock> ALTAR_VIEW = BLOCKS.register("altar_view", CraftingAltarViewBlock::new);
    public static final RegistryObject<GlassBlock> MAGIC_WALL = BLOCKS.register("magic_wall", () -> new GlassBlock(Block.Properties.create(Material.ICE).harvestLevel(0).hardnessAndResistance(3).harvestTool(ToolType.PICKAXE).slipperiness(0.6f).notSolid()));
    public static final RegistryObject<InscriptionTableBlock> INSCRIPTION_TABLE = BLOCKS.register("inscription_table", InscriptionTableBlock::new);
    public static final RegistryObject<OcculusBlock> OCCULUS = BLOCKS.register("occulus", OcculusBlock::new);
    public static final RegistryObject<Block> MANA_BATTERY = BLOCKS.register("mana_battery", () -> new Block(Block.Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> ESSENCE_REFINER = BLOCKS.register("essence_refiner", () -> new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> ARMOR_IMBUEMENT_TABLE = BLOCKS.register("armor_imbuement_table", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(4).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> SLIPSTREAM_GENERATOR = BLOCKS.register("slipstream_generator", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> DRAINING_WELL = BLOCKS.register("draining_well", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> FLICKER_LURE = BLOCKS.register("flicker_lure", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> EVERSTONE = BLOCKS.register("everstone", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Door> KEYSTONE_DOOR = BLOCKS.register("keystone_door", () -> new Door(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f, 2).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Door> SPELL_SEALED_DOOR = BLOCKS.register("spell_sealed_door", () -> new Door(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f, 2).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Trapdoor> KEYSTONE_TRAPDOOR = BLOCKS.register("keystone_trapdoor", () -> new Trapdoor(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5f, 2).harvestTool(ToolType.AXE)));

    public static void register() {
    }
}
