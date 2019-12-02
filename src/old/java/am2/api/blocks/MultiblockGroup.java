package am2.api.blocks;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.ImmutableList;

public class MultiblockGroup {
	
	protected ArrayList<BlockPos> positions;
	protected ArrayList<IBlockState> states;
	protected boolean ignoreState;
	protected String name;
	
	public MultiblockGroup(String name, ArrayList<IBlockState> arrayList, boolean ignoreState) {
		positions = new ArrayList<BlockPos>();
		this.states = arrayList;
		this.ignoreState = ignoreState;
		this.name = name;
	}
	
	public void addBlock(BlockPos position) {
		positions.add(position);
	}
	
	public void addState (IBlockState state) {
		states.add(state);
	}
	
	public boolean matches (World world, BlockPos startCheckPos) {
		boolean flag = true;
		for (BlockPos pos : positions) {
			if (ignoreState) {
				boolean subFlag = false;
				for (IBlockState state : states) {
					subFlag = world.getBlockState(startCheckPos.add(pos)).getBlock().equals(state.getBlock());
					if (subFlag)
						break;
				}
				flag = subFlag;
			}
			else {
				boolean subFlag = false;
				for (IBlockState state : states) {
					subFlag = world.getBlockState(startCheckPos.add(pos)).equals(state);
					if (subFlag)
						break;
				}
				flag = subFlag;
				
			}
			if (!flag) {
				break;
			}
		}
		
		return flag;
	}
	
//	public boolean matches (World world, BlockPos startCheckPos) {
//		boolean flag = false;
//		for (int i = getMinX(); i < getMaxX() && !flag; i++) {
//			for (int j = getMinY(); j < getMaxY() && !flag; j++) {
//				for (int k = getMinZ(); k < getMaxZ() && !flag; k++) {
//					BlockPos toAdd = new BlockPos(i, j, k);
//					for (int l = 0; l < 4 && !flag; l++) {
//						MultiblockGroup group = rotate(l);
//						flag = group.matches_internal(world, startCheckPos.add(toAdd));
//					}
//				}
//			}
//		}
//		return flag;
//	}
	
	public int getMinX () {
		int min = Integer.MAX_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getX() < min)
				min = pos.getX();
		}
		return min;
	}
	
	public int getMinY () {
		int min = Integer.MAX_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getY() < min)
				min = pos.getY();
		}
		return min;
	}
	
	public int getMinZ () {
		int min = Integer.MAX_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getZ() < min)
				min = pos.getZ();
		}
		return min;
	}
	
	public int getMaxX () {
		int max = Integer.MIN_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getX() > max)
				max = pos.getX();
		}
		return max;
	}
	
	public int getMaxY () {
		int max = Integer.MIN_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getY() > max)
				max = pos.getY();
		}
		return max;
	}
	
	public int getMaxZ () {
		int max = Integer.MIN_VALUE;
		for (BlockPos pos : positions) {
			if (pos.getZ() > max)
				max = pos.getZ();
		}
		return max;
	}
	
	/**
	 * 
	 * @param mode 1 orientation, 2 invert
	 * @return
	 */
	public MultiblockGroup rotate (int mode) {
		MultiblockGroup group = new MultiblockGroup(name, states, ignoreState);
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		
		for (BlockPos pos : positions) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (mode % 2 == 1) {
				int saveX = z;
				int saveZ = x;
				x = saveX;
				z = saveZ;
			}
			if (mode % 4 >= 2) {
				x = -x;
				z = -z;
			}
			
			positions.add(new BlockPos(x, y, z));
		}
		group.positions = positions;
		return group;
	}
	
	public ImmutableList<BlockPos> getPositions() {
		return ImmutableList.copyOf(positions);
	}
	
	public ImmutableList<IBlockState> getStates() {
		return ImmutableList.copyOf(states);
	}
}
