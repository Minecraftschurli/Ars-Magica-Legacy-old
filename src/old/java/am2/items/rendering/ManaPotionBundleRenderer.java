package am2.items.rendering;

import am2.defs.ItemDefs;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class ManaPotionBundleRenderer implements ItemMeshDefinition{
	
	private ModelResourceLocation defaultLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString(), "inventory");
	private ModelResourceLocation lesserLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString() + "_lesser", "inventory");
	private ModelResourceLocation standardLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString() + "_standard", "inventory");
	private ModelResourceLocation greaterLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString() + "_greater", "inventory");
	private ModelResourceLocation epicLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString() + "_epic", "inventory");
	private ModelResourceLocation legendaryLoc = new ModelResourceLocation(ItemDefs.manaPotionBundle.getRegistryName().toString() + "_legendary", "inventory");
	
	public ManaPotionBundleRenderer() {
		ModelBakery.registerItemVariants(ItemDefs.manaPotionBundle, defaultLoc, lesserLoc, standardLoc, greaterLoc, epicLoc, legendaryLoc);
		
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		int meta = stack.getItemDamage();
		if (meta < (1 << 8)) return lesserLoc;
		if (meta < (2 << 8)) return standardLoc;
		if (meta < (3 << 8)) return greaterLoc;
		if (meta < (4 << 8)) return epicLoc;
		if (meta < (5 << 8)) return legendaryLoc;
		return defaultLoc;
	}

}
