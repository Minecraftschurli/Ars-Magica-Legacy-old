package am2.world;

import java.util.Random;

import am2.defs.BlockDefs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WitchwoodTreeSmall extends WorldGenAbstractTree{
  
  private final int minTreeHeight = 4;
  
  private final IBlockState logBlock = BlockDefs.witchwoodLog.getDefaultState();
  private final IBlockState leafBlock = BlockDefs.witchwoodLeaves.getDefaultState();
  
  // this function is copied from WorldGenTrees.java
  // variable names have been changed for readability, and some un-used parts (vine, cocoa generation) have been removed
  public boolean generate(World world, Random random, BlockPos pos){
	  int height = random.nextInt(3) + this.minTreeHeight;
	  boolean isValidPlantingSpot = true;
	  
	  if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256){
		  byte leafMaxRadius;
		  IBlockState block;
		  
		  for (int j = pos.getY(); j <= pos.getY() + 1 + height; j++){
			  leafMaxRadius = 1;
			  
			  if (j == pos.getY()){
				  leafMaxRadius = 0;
			  }
			  
			  if (j >= pos.getY() + 1 + height - 2){
				  leafMaxRadius = 2;
			  }
			  
			  for (int i = pos.getX() - leafMaxRadius; i <= pos.getX() + leafMaxRadius && isValidPlantingSpot; i++){
				  for (int k = pos.getZ() - leafMaxRadius; k <= pos.getZ() + leafMaxRadius && isValidPlantingSpot; k++){
					  if (j >= 0 && j < 256){						  
						  if (!this.isReplaceable(world, new BlockPos (i, j, k))){
							  isValidPlantingSpot = false;
						  }
					  }
					  else{
						  isValidPlantingSpot = false;
					  }
				  }
			  }
		  }
		  
		  if (!isValidPlantingSpot){
			  return false;
		  }
		  else{
			  Block block2 = world.getBlockState(pos.down()).getBlock();
			  
			  boolean isSoil = block2.canSustainPlant(world.getBlockState(pos.down()), world, pos.down(), EnumFacing.UP, (BlockSapling)Blocks.SAPLING);
			  if (isSoil && pos.getY() < 256 - height - 1){
				  block2.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);
				  leafMaxRadius = 3;
				  byte b1 = 0;
				  
				  for (int j = pos.getY() - leafMaxRadius + height; j <= pos.getY() + height; j++){
					  int treeHeightLayer = j - (pos.getY() + height);
					  int leafGenRadius = b1 + 1 - treeHeightLayer / 2;
					  
					  for (int i = pos.getX() - leafGenRadius; i <= pos.getX() + leafGenRadius; i++){
						  int leafGenDeltaX = i - pos.getX();
						  
						  for (int k = pos.getZ() - leafGenRadius; k <= pos.getZ() + leafGenRadius; k++){
							  int leafGenDeltaZ = k - pos.getZ();
							  
							  if (Math.abs(leafGenDeltaX) != leafGenRadius || Math.abs(leafGenDeltaZ) != leafGenRadius || random.nextInt(2) != 0 && treeHeightLayer != 0){
								  BlockPos leafPos = new BlockPos(i, j, k);
								  IBlockState block1 = world.getBlockState(leafPos);
								  
								  if (block1.getBlock().isAir(block1, world, leafPos) || block1.getBlock().isLeaves(block1, world, leafPos)){
									  world.setBlockState(leafPos, leafBlock);
								  }
							  }
						  }
					  }
				  }
				  
				  for (int j = 0; j < height; j++){
					  block = world.getBlockState(pos.add(0, j, 0));
					  
					  if (block.getBlock().isAir(block, world, pos.add(0, j, 0)) || block.getBlock().isLeaves(block, world, pos.add(0, j, 0))){
						  this.setBlockAndNotifyAdequately(world, pos.add(0, j, 0), logBlock);
					  }
				  }
				  
				  return true;
			  }
			  else{
				  return false;
			  }
		  }
	  }
	  else{
		  return false;
	  }
  }
  
  
  public WitchwoodTreeSmall(boolean par1){
	super(par1);
  }
  
}
