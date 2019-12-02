package am2.lore;

import java.util.ArrayList;

import am2.api.compendium.CompendiumCategory;
import am2.api.compendium.CompendiumEntry;
import am2.api.extensions.IArcaneCompendium;
import am2.api.extensions.IDataSyncExtension;
import am2.defs.ItemDefs;
import am2.extensions.DataDefinitions;
import am2.extensions.datamanager.DataSyncExtension;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ArcaneCompendium implements IArcaneCompendium, ICapabilityProvider, ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IArcaneCompendium.class)
	public static Capability<IArcaneCompendium> INSTANCE = null;
	
	public static Achievement compendiumData = (new Achievement("am2_ach_data", "compendiumData", 0, 0, ItemDefs.arcaneCompendium, null));
	public static Achievement componentUnlock = (new Achievement("am2_ach_unlock", "componentUnlock", 0, 0, ItemDefs.spellParchment, null));
	
	private EntityPlayer player;
	private String path = "";
	public ArcaneCompendium() {}
	
	public void unlockEntry(String name) {
		ArrayList<String> compendium = DataSyncExtension.For(player).get(DataDefinitions.COMPENDIUM);
		compendium.add(name);
		DataSyncExtension.For(player).setWithSync(DataDefinitions.COMPENDIUM, compendium);
	}
	
	public boolean isUnlocked(String name) {
		for (String str : DataSyncExtension.For(player).get(DataDefinitions.COMPENDIUM)) {
			if (str.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public void init(EntityPlayer player, IDataSyncExtension ext) {
		this.player = player;
		ext.setWithSync(DataDefinitions.COMPENDIUM, new ArrayList<String>());
	}

	public static IArcaneCompendium For(EntityPlayer entityPlayer) {
		return entityPlayer.getCapability(INSTANCE, null);
	}

	@Override
	public boolean isNew(String id) {
		return false;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == INSTANCE)
			return (T) this;
		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return new IArcaneCompendium.Storage().writeNBT(INSTANCE, this, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		new IArcaneCompendium.Storage().readNBT(INSTANCE, this, null, nbt);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String str) {
		this.path = str;
	}

	@Override
	public void unlockRelatedItems(ItemStack crafting) {
		for (CompendiumEntry entry : CompendiumCategory.getAllEntries()) {
			Object obj = entry.getRenderObject();
			if (obj == null)
				continue;
			else if (obj instanceof ItemStack && ((ItemStack)obj).isItemEqual(crafting))
				unlockEntry(entry.getID());
			else if (obj instanceof Item && crafting.getItem() == obj)
				unlockEntry(entry.getID());
			else if (obj instanceof Block && crafting.getItem() instanceof ItemBlock && ((ItemBlock)crafting.getItem()).block == obj)
				unlockEntry(entry.getID());
		}
	}

	@Override
	public ArrayList<CompendiumEntry> getEntriesForCategory(String categoryName) {
		return null;
	}
}
