package am2.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockSubtypes extends ItemBlock {

	public ItemBlockSubtypes(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}

}
