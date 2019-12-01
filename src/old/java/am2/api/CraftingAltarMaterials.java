package am2.api;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;

import am2.blocks.BlockArsMagicaBlock;
import am2.defs.BlockDefs;
import am2.utils.KeyValuePair;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class CraftingAltarMaterials {
	
	private static final HashMap<IBlockState, Integer> caps = new HashMap<>();
	private static final HashMap<KeyValuePair<IBlockState, IBlockState>, Integer> main = new HashMap<>();
	
	static {
		addCapsMaterial(Blocks.GLASS.getDefaultState(), 1);
		addCapsMaterial(Blocks.COAL_BLOCK.getDefaultState(), 2);
		addCapsMaterial(Blocks.REDSTONE_BLOCK.getDefaultState(), 3);
		addCapsMaterial(Blocks.IRON_BLOCK.getDefaultState(), 4);
		addCapsMaterial(Blocks.LAPIS_BLOCK.getDefaultState(), 5);
		addCapsMaterial(Blocks.GOLD_BLOCK.getDefaultState(), 6);
		addCapsMaterial(Blocks.DIAMOND_BLOCK.getDefaultState(), 7);
		addCapsMaterial(Blocks.EMERALD_BLOCK.getDefaultState(), 8);
		addCapsMaterial(BlockDefs.blocks.getDefaultState().withProperty(BlockArsMagicaBlock.BLOCK_TYPE, BlockArsMagicaBlock.EnumBlockType.MOONSTONE), 9);
		addCapsMaterial(BlockDefs.blocks.getDefaultState().withProperty(BlockArsMagicaBlock.BLOCK_TYPE, BlockArsMagicaBlock.EnumBlockType.SUNSTONE), 10);
		
		addMainMaterial(Blocks.PLANKS.getDefaultState(), Blocks.OAK_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA), Blocks.ACACIA_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH), Blocks.BIRCH_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE), Blocks.SPRUCE_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE), Blocks.JUNGLE_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK), Blocks.DARK_OAK_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
		addMainMaterial(Blocks.QUARTZ_BLOCK.getDefaultState(), Blocks.QUARTZ_STAIRS.getDefaultState(), 3);
		addMainMaterial(Blocks.STONEBRICK.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE_STAIRS.getDefaultState(), 1);
		addMainMaterial(Blocks.PURPUR_BLOCK.getDefaultState(), Blocks.PURPUR_STAIRS.getDefaultState(), 4);
		addMainMaterial(Blocks.BRICK_BLOCK.getDefaultState(), Blocks.BRICK_STAIRS.getDefaultState(), 2);
		addMainMaterial(Blocks.RED_SANDSTONE.getDefaultState(), Blocks.RED_SANDSTONE_STAIRS.getDefaultState(), 2);
		addMainMaterial(BlockDefs.witchwoodPlanks.getDefaultState(), BlockDefs.witchwoodStairs.getDefaultState(), 3);
	}
	
	public static void addCapsMaterial (IBlockState state, int value) {
		caps.put(state, Integer.valueOf(value));
	}
	
	public static void addMainMaterial (IBlockState state, IBlockState stairs, int value) {
		main.put(new KeyValuePair<>(state, stairs), Integer.valueOf(value));
	}
	
	public static ImmutableMap<IBlockState, Integer> getCapsMap() {
		return ImmutableMap.copyOf(caps);
	}
	
	public static ImmutableMap<KeyValuePair<IBlockState, IBlockState>, Integer> getMainMap() {
		return ImmutableMap.copyOf(main);
	}
	
	public static ImmutableMap<IBlockState, Integer> getSimpleMainMap() {
		ImmutableMap.Builder<IBlockState, Integer> builder = ImmutableMap.builder();
		for (Entry<KeyValuePair<IBlockState, IBlockState>, Integer> entry : getMainMap().entrySet()) {
			builder.put(entry.getKey().key, entry.getValue());
		}
		return builder.build();
	}
}