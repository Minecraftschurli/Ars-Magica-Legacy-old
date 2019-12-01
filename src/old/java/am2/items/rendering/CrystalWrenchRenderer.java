package am2.items.rendering;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrystalWrenchRenderer implements ItemMeshDefinition {
	
	private ModelResourceLocation loc_default = new ModelResourceLocation("arsmagica2:crystal_wrench", "inventory");
	private ModelResourceLocation loc_disconnect = new ModelResourceLocation("arsmagica2:crystal_wrench_disconnect", "inventory");
	private ModelResourceLocation loc_stored = new ModelResourceLocation("arsmagica2:crystal_wrench_stored", "inventory");
	
	private static String KEEP_BINDING = "KEEPBINDING";
	private static String MODE = "WRENCHMODE";

	private static final int MODE_DISCONNECT = 1;
	
	public CrystalWrenchRenderer(Item crystalWrench) {
		ModelBakery.registerItemVariants(crystalWrench, loc_default, loc_disconnect, loc_stored);
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		if (stack.getTagCompound() != null){
			if (stack.getTagCompound().hasKey(KEEP_BINDING))
				return loc_stored;
			else if (stack.getTagCompound().hasKey(MODE) && stack.getTagCompound().getInteger(MODE) == MODE_DISCONNECT)
				return loc_disconnect;
			else
				return loc_default;
		}else{
			return loc_default;
		}
	}

}
