package am2.api.extensions;

import am2.extensions.datamanager.*;
import am2.packet.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.capabilities.Capability.*;

import java.util.concurrent.*;

public interface IDataSyncExtension extends ICapabilityProvider{
	
	public static class Factory implements Callable<IDataSyncExtension> {
		@Override
		public IDataSyncExtension call() throws Exception {
			return new DataSyncExtension();
		}
	}
	
	public class Storage implements IStorage<IDataSyncExtension> {

		@Override
		public NBTBase writeNBT(Capability<IDataSyncExtension> capability, IDataSyncExtension instance, EnumFacing side) {
			return new NBTTagCompound();
		}

		@Override
		public void readNBT(Capability<IDataSyncExtension> capability, IDataSyncExtension instance, EnumFacing side, NBTBase nbt) {
			
		}

	}
	public void init(Entity entity);
	/**
	 * Get the data stored at some variable.
	 * 
	 * @param data : the data object
	 * @return the stored thing
	 */
	public <T> T get(SavedObject<T> data);
	/**
	 * Sets an object, if it changed, sync.
	 * 
	 * @param data
	 * @param object
	 */
	public <T> void set(SavedObject<T> data, T object);
	/**
	 * Used for registration, forces sync
	 * 
	 * @param data
	 * @param defaultValue
	 */
	public <T> void setWithSync(SavedObject<T> data, T defaultValue);
	public byte[] createUpdatePacket();
	public void handleUpdatePacket(AMDataReader in);
	
}
