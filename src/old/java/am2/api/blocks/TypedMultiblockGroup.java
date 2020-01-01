package am2.api.blocks;

import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class TypedMultiblockGroup extends MultiblockGroup{
	
	protected ArrayList<HashMap<Integer, IBlockState>> states;
	protected HashMap<BlockPos, Integer> groups = new HashMap<>();
	
	private static ArrayList<IBlockState> createStateList(ArrayList<HashMap<Integer, IBlockState>> arrayList) {
		ArrayList<IBlockState> stateMap = new ArrayList<>();
		for (HashMap<Integer, IBlockState> map : arrayList) {
			stateMap.addAll(map.values());
		}
		return stateMap;
	}
	
	public TypedMultiblockGroup(String name, ArrayList<HashMap<Integer, IBlockState>> arrayList, boolean ignoreState) {
		super(name, createStateList(arrayList), ignoreState);
		this.states = arrayList;
	}
	
	public void addBlock(BlockPos position, int group) {
		positions.add(position);
		groups.put(position, group);
	}
	
	@Override
	@Deprecated
	public void addBlock(BlockPos position) {
		addBlock(position, 0);
	}
	
	public int getGroup(BlockPos pos) {
		return groups.get(pos);
	}
	
	public ArrayList<IBlockState> getState(BlockPos pos) {
		ArrayList<IBlockState> state = new ArrayList<>();
		for (HashMap<Integer, IBlockState> map : states) {
			state.add(map.get(getGroup(pos)));
		}
		return state;
	}
	
	@Override
	public boolean matches(World world, BlockPos startCheckPos) {
		for (HashMap<Integer, IBlockState> map : states) {
			boolean subFlag = false;
			for (BlockPos pos : positions) {
				IBlockState checkState = world.getBlockState(startCheckPos.add(pos));
				IBlockState state = map.get(getGroup(pos));
				if (ignoreState) {
					subFlag = checkState.getBlock().equals(state.getBlock());
				}
				else {
					subFlag = checkState.getBlock() == state.getBlock() && checkState.getBlock().getMetaFromState(checkState) == state.getBlock().getMetaFromState(state);
				}
				if (!subFlag)
					break;
			}
			if (subFlag)
				return true;
		}
		return false;
	}
}
