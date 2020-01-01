package am2.defs;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;

public class CreativeTabsDefs {
	
	public static final CreativeTabs tabAM2Blocks = new CreativeTabs("am2.blocks") {public Item getTabIconItem() {return Item.getItemFromBlock(BlockDefs.occulus);}};
	public static final CreativeTabs tabAM2Items = new CreativeTabs("am2.items") {public Item getTabIconItem() {return ItemDefs.spellParchment;}};
}
