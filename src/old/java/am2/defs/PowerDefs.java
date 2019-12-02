package am2.defs;

import am2.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;

public class PowerDefs {
	
	
	public enum EssenceType {
		NEUTRAL,
		DARK,
		LIGHT
	}
	
	public class Essence {
		
		private EssenceType type;
		private int value;
		
		public Essence(EssenceType type, int value) {
			this.type = type;
			this.value = value;
		}
		
		public EssenceType getType() {
			return type;
		}
		
		public int getValue() {
			return value;
		}
		
		public void writeToNBT (NBTTagCompound nbt) {
			NBTTagCompound baseTag = NBTUtils.getEssenceTag(nbt);
			NBTTagCompound essenceTag = NBTUtils.addTag(baseTag, type.name().toLowerCase());
			essenceTag.setInteger("Value", value);
		}
		
	}
}
