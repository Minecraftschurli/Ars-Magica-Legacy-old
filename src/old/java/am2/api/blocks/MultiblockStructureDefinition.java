package am2.api.blocks;

import am2.gui.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class MultiblockStructureDefinition {
	
	public ArrayList<List<MultiblockGroup>> groups;
	String id;
	
	public MultiblockStructureDefinition(String id) {
		groups = new ArrayList<>();
		this.id = id;
	}
	
	public void addGroup (MultiblockGroup group, MultiblockGroup... rest) {
		groups.add(Lists.asList(group, rest));
	}
	
	public boolean matches (World world, BlockPos startCheckPos) {
		boolean subFlag = true;
		for (List<MultiblockGroup> subGroup : groups) {
			boolean groupCheck = false;
			boolean hasCheck = false;
			for (MultiblockGroup group : subGroup) {
				hasCheck = true;
				groupCheck |= group.matches(world, startCheckPos);
				//System.out.println(group.name + " " +groupCheck);
			}
			if (hasCheck)
				subFlag &= groupCheck;
		}
		return subFlag;
	}
	
	public List<MultiblockGroup> getMatchingGroups (World world, BlockPos startCheckPos) {
		List<MultiblockGroup> list = new ArrayList<>();
		for (List<MultiblockGroup> subGroup : groups) {
			for (MultiblockGroup group : subGroup) {
				if (group.matches(world, startCheckPos)) {
					list.add(group);
				}
			}
		}
		return list;
	}
	
	public HashMap<BlockPos, List<IBlockState>> getStructureLayer(MultiblockGroup selected, int layer) {
		HashMap<BlockPos, List<IBlockState>> stateMap = new HashMap<>();
		for (BlockPos entry : selected.getPositions()) {
			if (entry.getY() == layer)
				stateMap.put(entry, selected.getStates());
		}
		return stateMap;
	}
	
	public int getMinX () {
		int min = Integer.MAX_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMinX() < min)
					min = gr.getMinX();
			}
		}
		return min;
	}
	
	public int getMinY () {
		int min = Integer.MAX_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMinY() < min)
					min = gr.getMinY();
			}
		}
		return min;
	}
	
	public int getMinZ () {
		int min = Integer.MAX_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMinZ() < min)
					min = gr.getMinZ();
			}
		}
		return min;
	}
	
	public int getMaxX () {
		int max = Integer.MIN_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMaxX() > max)
					max = gr.getMaxX();
			}
		}
		return max;
	}
	
	public int getMaxY () {
		int max = Integer.MIN_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMaxY() > max)
					max = gr.getMaxY();
			}
		}
		return max;
	}
	
	public int getMaxZ () {
		int max = Integer.MIN_VALUE;
		for (List<MultiblockGroup> group : groups) {
			for (MultiblockGroup gr : group) {
				if (gr.getMaxZ() > max)
					max = gr.getMaxZ();
			}
		}
		return max;
	}

	public int getWidth() {
		return getMaxX() - getMinX();
	}
	
	public int getLength() {
		return getMaxZ() - getMinZ();
	}
	
	public int getHeight() {
		return getMaxY() - getMinY();
	}
	
	public String getId() {
		return id;
	}

	public ArrayList<MultiblockGroup> getGroups() {
		ArrayList<MultiblockGroup> list = new ArrayList<>();
		for (List<MultiblockGroup> groups : this.groups) {
			int num = new Random(AMGuiHelper.instance.getSlowTicker() * 4500L).nextInt(groups.size());
			MultiblockGroup group = groups.get(num);
			list.add(group);
		}
		return list;
	}
}
