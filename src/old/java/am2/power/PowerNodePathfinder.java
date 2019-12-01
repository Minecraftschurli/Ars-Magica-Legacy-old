package am2.power;

import java.util.ArrayList;
import java.util.List;

import am2.api.power.IPowerNode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class PowerNodePathfinder extends AStar<Vec3d>{

	private World world;
	private Vec3d end;
	private PowerTypes powerType;

	PowerNodePathfinder(World world, Vec3d start, Vec3d end, PowerTypes type){
		this.world = world;
		this.end = end;
		this.powerType = type;
	}

	private IPowerNode<?> getPowerNode(World world, Vec3d location){
		if (world.getChunkFromBlockCoords(new BlockPos(location)) != null){
			Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(location));
			if (chunk.isLoaded()){
				TileEntity te = world.getTileEntity(new BlockPos(location));
				if (te instanceof IPowerNode)
					return (IPowerNode<?>)te;
			}
		}
		return null;
	}

	@Override
	protected boolean isGoal(Vec3d node){
		return node.equals(end);
	}

	@Override
	protected Double g(Vec3d from, Vec3d to){
		return from.squareDistanceTo(to);
	}

	@Override
	protected Double h(Vec3d from, Vec3d to){
		return from.squareDistanceTo(to);
	}

	@Override
	protected List<Vec3d> generateSuccessors(Vec3d node){
		IPowerNode<?> powerNode = getPowerNode(world, node);
		if (powerNode == null)
			return new ArrayList<Vec3d>();

		IPowerNode<?>[] candidates = PowerNodeRegistry.For(world).getAllNearbyNodes(world, node, powerType);

		ArrayList<Vec3d> prunedCandidates = new ArrayList<Vec3d>();
		for (IPowerNode<?> candidate : candidates){
			if (verifyCandidate(candidate)){
				prunedCandidates.add(new Vec3d(((TileEntity)candidate).getPos()));
			}
		}

		return prunedCandidates;
	}

	private boolean verifyCandidate(IPowerNode<?> powerNode){
		if (new Vec3d(((TileEntity)powerNode).getPos()).equals(end)){
			for (PowerTypes type : powerNode.getValidPowerTypes())
				if (type == powerType)
					return true;
		}
		return powerNode.canRelayPower(powerType);
	}
}
