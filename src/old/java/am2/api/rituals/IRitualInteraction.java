package am2.api.rituals;

import am2.api.blocks.MultiblockStructureDefinition;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRitualInteraction {
	
	public ItemStack[] getReagents();
	public int getReagentSearchRadius();
	public MultiblockStructureDefinition getRitualShape();
	@SideOnly(Side.CLIENT)
	public ItemStack getResult();
	
	public static class Wrapper {
		
		private final IRitualInteraction interaction;
		
		public Wrapper(IRitualInteraction interaction) {
			this.interaction = interaction;
		}
		
		public IRitualInteraction getRitualInteraction() {
			return interaction;
		}
		
	}
}
