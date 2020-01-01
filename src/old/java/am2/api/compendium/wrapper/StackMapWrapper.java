package am2.api.compendium.wrapper;

import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.util.text.translation.*;

import java.util.*;
import java.util.Map.*;

@SuppressWarnings("deprecation")
public class StackMapWrapper {
	
	private static Map<ItemStack, Integer> wrapBlockMap(Map<IBlockState, Integer> input) {
		HashMap<ItemStack, Integer> wrappedMap = new HashMap<>();
		for (Entry<IBlockState, Integer> entry : input.entrySet()) {
			if (entry.getValue() == null) continue;
			if (entry.getKey() == null) continue;
			Item item = Item.getItemFromBlock(entry.getKey().getBlock());
			if (item == null) continue;
			wrappedMap.put(new ItemStack(item, 1, entry.getKey().getBlock().getMetaFromState(entry.getKey())), entry.getValue());
		}
		
		return wrappedMap;
	}
	
	private static Map<ItemStack, Integer> removeNullItems(Map<ItemStack, Integer> input) {
		HashMap<ItemStack, Integer> wrappedMap = new HashMap<>();
		
		for (Entry<ItemStack, Integer> entry : input.entrySet()) {
			if (entry.getValue() == null) continue;
			if (entry.getKey() == null) continue;
			wrappedMap.put(entry.getKey(), entry.getValue());
		}
		
		return wrappedMap;
	}
	
	private static Map<ItemStack, Integer> orderMap(Map<ItemStack, Integer> input) {
		int max = -1;
		for (Entry<ItemStack, Integer> entry : input.entrySet()) {
			if (entry.getValue() != null && entry.getValue().intValue() > max)
				max = entry.getValue().intValue();
		}
		HashMap<ItemStack, Integer> wrappedMap = new HashMap<>();
		
		for (int i = 0; i <= max; i++) {
			for (Entry<ItemStack, Integer> entry : input.entrySet()) {
				if (entry.getValue() != null && entry.getValue().intValue() == i) {
					wrappedMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return wrappedMap;
	}
	
	private final Map<ItemStack, Integer> input;
	private final String name;
	
	public StackMapWrapper(Map<ItemStack, Integer> input, String name) {
		this.input = orderMap(removeNullItems(input));
		this.name = name;
	}
	
	public StackMapWrapper(Map<IBlockState, Integer> input, String name, boolean placeholder) {
		this(wrapBlockMap(input), name);
	}
	
	public Map<ItemStack, Integer> getInput() {
		return input;
	}
	
	public String getUnlocalizedName() {
		return name;
	}
	
	public String getLocalizedName() {
		return I18n.translateToLocal(getUnlocalizedName());
	}
}
