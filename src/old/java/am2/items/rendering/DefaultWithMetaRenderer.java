package am2.items.rendering;

import java.util.HashMap;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class DefaultWithMetaRenderer extends IgnoreMetadataRenderer {
	
	private HashMap<Integer, ModelResourceLocation> metadatas = new HashMap<>();
	
	public DefaultWithMetaRenderer(ModelResourceLocation loc) {
		super(loc);
	}
	
	public void addModel(int meta, ModelResourceLocation loc) {
		metadatas.put(Integer.valueOf(meta), loc);
	}
	
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		ModelResourceLocation loc = metadatas.get(stack.getItemDamage());
		if (loc != null)
			return loc;
		return super.getModelLocation(stack);
	}
}
