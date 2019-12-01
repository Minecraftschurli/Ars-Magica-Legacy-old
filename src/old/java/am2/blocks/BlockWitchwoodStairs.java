package am2.blocks;

import am2.defs.CreativeTabsDefs;
import am2.items.ItemBlockSubtypes;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWitchwoodStairs extends BlockStairs{

	public BlockWitchwoodStairs(IBlockState state){
		super(state);
		this.setHardness(2.0f);
		this.setResistance(2.0f);
		this.setHarvestLevel("axe", 2);
		this.setCreativeTab(CreativeTabsDefs.tabAM2Blocks);
	}

	public BlockStairs registerAndName(ResourceLocation rl) {
		this.setUnlocalizedName(rl.toString());
		GameRegistry.register(this, rl);
		GameRegistry.register(new ItemBlockSubtypes(this), rl);
		return this;
	}
}
