package am2.blocks.tileentity;

import am2.api.ArsMagicaAPI;
import am2.api.DamageSources;
import am2.api.blocks.IKeystoneLockable;
import am2.items.ItemFocusCharge;
import am2.items.ItemFocusMana;
import am2.power.PowerNodeRegistry;
import am2.power.PowerTypes;
import am2.spell.component.Summon;
import am2.utils.DummyEntityPlayer;
import am2.utils.EntityUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

public class TileEntitySummoner extends TileEntityAMPower implements IInventory, IKeystoneLockable<TileEntitySummoner>{

	private static final float summonCost = 2000;
	private static final float maintainCost = 7.5f;
	private ItemStack[] inventory;
	private int summonEntityID = -1;
	private DummyEntityPlayer dummyCaster;
	private int summonCooldown = 0;
	private int prevSummonCooldown = 0;
	private static final int maxSummonCooldown = 200;
	private static final int powerPadding = 500; //extra power to charge before summoning so that it can be maintained for a while

	private static final int SUMMON_SLOT = 3;

	public TileEntitySummoner(){
		super(2500);
		inventory = new ItemStack[getSizeInventory()];
	}

	private boolean isRedstonePowered(){
		return this.worldObj.isBlockIndirectlyGettingPowered(pos) > 0;
	}

	@Override
	public void update(){
		super.update();

		prevSummonCooldown = summonCooldown;
		summonCooldown--;
		if (summonCooldown < 0) summonCooldown = 0;

		if (!worldObj.isRemote && summonCooldown == 0 && prevSummonCooldown > 0){
			worldObj.markAndNotifyBlock(pos, worldObj.getChunkFromBlockCoords(pos), worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
		}

		if (!worldObj.isRemote){
			EntityLiving ent = getSummonedCreature();
			if (ent == null){
				summonEntityID = -1;
			}
			if (isRedstonePowered() && inventory[SUMMON_SLOT] != null){
				if (PowerNodeRegistry.For(this.worldObj).checkPower(this, maintainCost)){
					if (ent == null && canSummon()){
						summonCreature();
					}else{
						if (ent != null){
							PowerNodeRegistry.For(this.worldObj).consumePower(this, PowerNodeRegistry.For(this.worldObj).getHighestPowerType(this), maintainCost);
						}
					}
				}else{
					unsummonCreature();
				}
			}else{
				if (ent != null){
					unsummonCreature();
					PowerNodeRegistry.For(this.worldObj).insertPower(this, PowerTypes.NEUTRAL, summonCost / 2);
				}
			}
		}
	}

	public float getSummonCost(){
		int numManaFoci = numFociOfType(ItemFocusMana.class);
		return summonCost * (1.0f - 0.2f * numManaFoci);
	}

	public float getMaintainCost(){
		int numManaFoci = numFociOfType(ItemFocusMana.class);
		return maintainCost * (1.0f - 0.2f * numManaFoci);
	}

	public boolean canSummon(){
		if (this.worldObj == null)
			return false;
		return summonCooldown == 0 && PowerNodeRegistry.For(this.worldObj).checkPower(this, getSummonCost() + powerPadding);
	}

	public boolean hasSummon(){
		return this.summonEntityID != -1;
	}

	private void summonCreature(){
		if (worldObj.isRemote || this.summonEntityID != -1) return;
		if (dummyCaster == null){
			dummyCaster = new DummyEntityPlayer(worldObj);
		}
		EntityLiving summon = ((Summon)ArsMagicaAPI.getSpellRegistry().getObject(new ResourceLocation("arsmagica2:summon"))).summonCreature(inventory[SUMMON_SLOT], dummyCaster, dummyCaster, worldObj, pos.getX(), pos.getY() + 1, pos.getZ());
		if (summon != null){
			if (summon instanceof EntityCreature)
				EntityUtils.setGuardSpawnLocation((EntityCreature)summon, pos.getX(), pos.getY(), pos.getZ());
			this.summonEntityID = summon.getEntityId();
			PowerNodeRegistry.For(this.worldObj).consumePower(this, PowerNodeRegistry.For(this.worldObj).getHighestPowerType(this), summonCost);
			this.summonCooldown = TileEntitySummoner.maxSummonCooldown;
			EntityUtils.setTileSpawned(summon, this);
			worldObj.markAndNotifyBlock(pos, worldObj.getChunkFromBlockCoords(pos), worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
		}
	}

	private void unsummonCreature(){
		if (worldObj.isRemote) return;
		EntityLiving ent = getSummonedCreature();
		if (ent == null) return;
		ent.attackEntityFrom(DamageSources.unsummon, 1000000);
		this.summonEntityID = -1;
		worldObj.markAndNotifyBlock(pos, worldObj.getChunkFromBlockCoords(pos), worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
	}

	private EntityLiving getSummonedCreature(){
		if (this.summonEntityID == -1) return null;
		return (EntityLiving)worldObj.getEntityByID(this.summonEntityID);
	}

	@Override
	public int getSizeInventory(){
		return 7;
	}

	@Override
	public ItemStack[] getRunesInKey(){
		ItemStack[] runes = new ItemStack[3];
		runes[0] = inventory[4];
		runes[1] = inventory[5];
		runes[2] = inventory[6];
		return runes;
	}

	@Override
	public boolean keystoneMustBeHeld(){
		return false;
	}

	@Override
	public boolean keystoneMustBeInActionBar(){
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		if (i < 0 || i >= getSizeInventory()) return null;
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j){
		if (inventory[i] != null){
			if (inventory[i].stackSize <= j){
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0){
				inventory[i] = null;
			}
			return itemstack1;
		}else{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int i){
		if (inventory[i] != null){
			ItemStack itemstack = inventory[i];
			inventory[i] = null;
			return itemstack;
		}else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack){
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()){
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getName(){
		return "Summoner";
	}

	@Override
	public boolean hasCustomName(){
		return false;
	}

	@Override
	public int getInventoryStackLimit(){
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer){
		if (worldObj.getTileEntity(pos) != this){
			return false;
		}
		return entityplayer.getDistanceSqToCenter(pos) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer player){
	}

	@Override
	public void closeInventory(EntityPlayer player){
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack){
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound){
		super.readFromNBT(nbttagcompound);

		NBTTagList nbttaglist = nbttagcompound.getTagList("SummonerInventory", Constants.NBT.TAG_COMPOUND);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++){
			String tag = String.format("ArrayIndex", i);
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte(tag);
			if (byte0 >= 0 && byte0 < inventory.length){
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound){
		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++){
			if (inventory[i] != null){
				String tag = String.format("ArrayIndex", i);
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte(tag, (byte)i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("SummonerInventory", nbttaglist);
		return nbttagcompound;
	}

	private int numFociOfType(Class<?> type){
		int count = 0;
		for (int i = 0; i < 3; ++i){
			if (inventory[i] != null && type.isInstance(inventory[i].getItem())){
				count++;
			}
		}
		return count;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(pos, 0, compound);
		return packet;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public int getChargeRate(){
		int numChargeFoci = numFociOfType(ItemFocusCharge.class);
		return 100 + (50 * numChargeFoci);
	}

	@Override
	public boolean canRelayPower(PowerTypes type){
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
