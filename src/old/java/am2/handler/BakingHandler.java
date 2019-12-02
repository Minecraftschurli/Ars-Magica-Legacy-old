package am2.handler;

import java.util.Map;
import java.util.Map.Entry;

import am2.api.CraftingAltarMaterials;
import am2.api.event.GenerateCompendiumLoreEvent;
import am2.api.event.OBJQuadEvent;
import am2.utils.KeyValuePair;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BakingHandler {
	
	@SubscribeEvent
	public void objBake (OBJQuadEvent event) {
		if (event.materialName.equalsIgnoreCase("Colored")) {
			event.tintIndex = 1;
		}
	}
	
	@SubscribeEvent
	public void handleSpecialCompendiumText(GenerateCompendiumLoreEvent event) {
		if (event.entry.equals("craftingAltar")) {
			Map<KeyValuePair<IBlockState, IBlockState>, Integer> map = CraftingAltarMaterials.getMainMap();
			int max = -1;
			for (Entry<KeyValuePair<IBlockState, IBlockState>, Integer> entry : map.entrySet()) {
				if (entry.getValue() != null && entry.getValue().intValue() > max)
					max = entry.getValue().intValue();
			}
			String text = "!d ";
			for (int i = 0; i <= max; i++) {
				for (Entry<KeyValuePair<IBlockState, IBlockState>, Integer> entry : map.entrySet()) {
					if (entry.getValue() != null && entry.getValue().intValue() == i) {
						Item item = Item.getItemFromBlock(entry.getKey().key.getBlock());
						if (item != null) {
							text += item.getItemStackDisplayName(new ItemStack(entry.getKey().key.getBlock(), 1, entry.getKey().key.getBlock().getMetaFromState(entry.getKey().key)));
							text += " ( +" + i + " Effects) !l ";
						}
					}
				}				
			}
			event.lore = event.lore.replaceAll("<main_power_map>", text);
			
			Map<IBlockState, Integer> capsmap = CraftingAltarMaterials.getCapsMap();
			int capsmax = -1;
			for (Entry<IBlockState, Integer> entry : capsmap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().intValue() > capsmax)
					capsmax = entry.getValue().intValue();
			}
			text = "!d ";
			for (int i = 0; i <= capsmax; i++) {
				for (Entry<IBlockState, Integer> entry : capsmap.entrySet()) {
					if (entry.getValue() != null && entry.getValue().intValue() == i) {
						Item item = Item.getItemFromBlock(entry.getKey().getBlock());
						if (item != null) {
							text += item.getItemStackDisplayName(new ItemStack(entry.getKey().getBlock(), 1, entry.getKey().getBlock().getMetaFromState(entry.getKey())));
							text += " ( +" + i + " Effects) !l ";
						}
					}
				}				
			}
			event.lore = event.lore.replaceAll("<caps_power_map>", text);
			event.lore = event.lore.replaceAll("<max_spell_data>", (capsmax + max) + "");
		}
	}
	
}
