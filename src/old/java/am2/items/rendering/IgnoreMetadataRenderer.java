package am2.items.rendering;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class IgnoreMetadataRenderer implements ItemMeshDefinition {
	
	private final ModelResourceLocation loc;
	
	public IgnoreMetadataRenderer(ModelResourceLocation loc) {
		this.loc = loc;
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return loc;
	}

}
