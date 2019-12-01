package am2.items.colorizers;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemSpellBookColorizer implements IItemColor{

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		int meta = stack.getItemDamage();
		switch (meta) {
		case 0: // brown
			return 0x744c14;
		case 1: // cyan
			return 0x16d9c9;
		case 2: // gray
			return 0x9b9b9b;
		case 3: // light blue
			return 0x5798cb;
		case 4: // white
			return 0xffffff;
		case 5: // black
			return 0x000000;
		case 6: // orange
			return 0xde8317;
		case 7: // purple
			return 0xa718bc;
		case 8: // blue
			return 0x0b11ff;
		case 9: // green
			return 0x1bbf1b;
		case 10: // yellow
			return 0xe8dd29;
		case 11: // red
			return 0xde1717;
		case 12: // lime
			return 0x00ff0c;
		case 13: // pink
			return 0xffc0cb;
		case 14: // magenta
			return 0xFF00FF;
		case 15: // light gray
			return 0xd4d4d4;
		default:
			return 0x744c14;
		}
	}
}
