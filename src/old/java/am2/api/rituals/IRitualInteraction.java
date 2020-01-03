package am2.api.rituals;

import am2.api.blocks.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

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
