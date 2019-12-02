package am2.entity;

import java.util.ArrayList;

import am2.ArsMagica2;
import am2.api.ArsMagicaAPI;
import am2.api.affinity.Affinity;
import am2.api.event.FlickerAffinityEvent;
import am2.api.math.AMVector3;
import am2.armor.ArmorHelper;
import am2.armor.infusions.GenericImbuement;
import am2.defs.BlockDefs;
import am2.defs.ItemDefs;
import am2.particles.*;
import am2.utils.InventoryUtilities;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;

public class EntityFlicker extends EntityAmbientCreature{

	private static final DataParameter<Integer> WATCHER_FLICKERTYPE = EntityDataManager.createKey(EntityFlicker.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> WATCHER_AMBIENTFLICK = EntityDataManager.createKey(EntityFlicker.class, DataSerializers.BOOLEAN);

	private static final int DIRECTION_CHANGE_TIME = 200;

	private AMVector3 targetPosition = null;
	private AMVector3 normalizedMovementVector = AMVector3.zero();

	private int flickCount = 0;

	public EntityFlicker(World par1World){
		super(par1World);
		this.setSize(0.5f, 0.5f);
		setFlickerType(ArsMagicaAPI.getAffinityRegistry().getRandomObject(getRNG()));
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		this.dataManager.register(WATCHER_FLICKERTYPE, 0);
		this.dataManager.register(WATCHER_AMBIENTFLICK, false);
	}

	@Override
	public void setDead(){
		ArsMagica2.proxy.decrementFlickerCount();
		super.setDead();
	}

	public void setFlickerType(Affinity affinity){
		this.dataManager.set(WATCHER_FLICKERTYPE, ArsMagicaAPI.getAffinityRegistry().getId(affinity));
	}

	public Affinity getFlickerAffinity(){
		return ArsMagicaAPI.getAffinityRegistry().getObjectById(dataManager.get(WATCHER_FLICKERTYPE));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2){
		if (this.worldObj.isRemote)
			return false;
		flick();
		return !par1DamageSource.isUnblockable();
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate(){
		return true;
	}

	@Override
	public void fall(float par1, float par2){
	}

	@Override
	public boolean canBePushed(){
		return false;
	}

	@Override
	public boolean canBreatheUnderwater(){
		return true;
	}

	@Override
	public boolean canTriggerWalking(){
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound){
		ArsMagica2.proxy.incrementFlickerCount();
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();

		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		long time = worldObj.getWorldTime() % 24000;
		if (!worldObj.isRemote && time >= 18500 && time <= 18600){
			//this.dataManager.set(WATCHER_AMBIENTFLICK, (byte)1);
			this.setDead();
			return;
		}

		boolean playerClose = false;
		AMVector3 me = new AMVector3(this);
		if (!worldObj.isRemote){
			for (Object o : worldObj.playerEntities){
				if (me.distanceSqTo(new AMVector3((EntityPlayer)o)) < 25){

					ItemStack chestArmor = ((EntityPlayer)o).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
					if (chestArmor == null || !ArmorHelper.isInfusionPreset(chestArmor, GenericImbuement.flickerLure))
						playerClose = true;
					break;
				}
			}
		}

		if (this.ticksExisted > 100 && playerClose && !this.dataManager.get(WATCHER_AMBIENTFLICK)){
			if (this.getActivePotionEffects().size() == 0 || (this.getActivePotionEffects().size() == 1 && worldObj.rand.nextDouble() < 0.1f))
				this.dataManager.set(WATCHER_AMBIENTFLICK, true);
		}else if (this.dataManager.get(WATCHER_AMBIENTFLICK)){
			flickCount++;
			if (worldObj.isRemote && flickCount > 7)
				flick(); //client flick
			else if (!worldObj.isRemote && flickCount > 10)
				flick(); //server flick
		}

		if (worldObj.isRemote){
			//for (int i = 0; i < + 1; ++i){
			if (getRNG().nextInt(10) < ArsMagica2.config.getGFXLevel()){
				AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(worldObj, AMParticleDefs.getParticleForAffinity(getFlickerAffinity()), posX, posY, posZ);
				if (effect != null){
					effect.addRandomOffset(this.width, this.height, this.width);
					effect.setDontRequireControllers();
					effect.setMaxAge(10);
					if (getFlickerAffinity() == Affinity.EARTH)
						effect.setParticleScale(0.01f + rand.nextFloat() * 0.05f);
					else
						effect.setParticleScale(0.05f + rand.nextFloat() * 0.05f);
				}
			}
		}
	}
	
	

	@Override
	protected void updateAITasks(){
		super.updateAITasks();

		AMVector3 me = new AMVector3(this);

		boolean needsNewPath = targetPosition == null || this.ticksExisted % DIRECTION_CHANGE_TIME == 0;
		if (needsNewPath && worldObj.rand.nextDouble() < 0.1f)
			pickNewTargetPosition();

		if (targetPosition == null) //this represents the pause state in between picking new waypoints
			return;
		
		if (me.distanceSqTo(targetPosition) > 400f){
			targetPosition = null;
			return;
		}
		
		if (me.distanceSqTo(targetPosition) < 1f){
			targetPosition = null;
			return;
		}

		this.rotationYaw = AMVector3.anglePreNorm(me, targetPosition);

		normalizedMovementVector = me.copy().sub(targetPosition).normalize();

		if (normalizedMovementVector.y > 0)
			rotatePitchTowards(-70 * normalizedMovementVector.y, 30);
		else
			rotatePitchTowards(0, 30);

		float speed = 0.2f;
		this.addVelocity(-normalizedMovementVector.x * speed, -normalizedMovementVector.y * speed, -normalizedMovementVector.z * speed);
	}

	public AMVector3 getNormalizedMovement(){
		return this.normalizedMovementVector;
	}

	private void rotatePitchTowards(float p, float step){
		if (this.rotationPitch != p){
			if (step > 0 && this.rotationPitch + step > p){
				step = p - this.rotationPitch;
			}else if (step < 0 && this.rotationPitch + step < p){
				step = p - this.rotationPitch;
			}
			this.rotationPitch += step;
		}
	}

	private void flick(){
		if (this.worldObj.isRemote){
			for (int i = 0; i < 10 * ArsMagica2.config.getGFXLevel(); ++i){
				AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(worldObj, "radiant", posX, posY, posZ);
				if (particle != null){
					particle.AddParticleController(
							new ParticleMoveOnHeading(
									particle,
									worldObj.rand.nextDouble() * 360,
									worldObj.rand.nextDouble() * 360,
									worldObj.rand.nextDouble() * 0.3f + 0.01f,
									1,
									false));
					particle.setRGBColorI(getFlickerAffinity().getColor());
					particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed((float)(worldObj.rand.nextDouble() * 0.1 + 0.05)).setKillParticleOnFinish(true));
					particle.setIgnoreMaxAge(true);
					particle.setParticleScale(0.1f);
				}
			}
		}else{
			this.setDead();
		}
	}

	private void pickNewTargetPosition(){
		int groundLevel = 0;
		Affinity aff = this.getFlickerAffinity();
		for (int x = -5; x <= 5; x++) {
			for (int y = 0; y <= 5; y++) {
				for (int z = 0; z <= 5; z++) {
					if (worldObj.getBlockState(getPosition().add(x, y, z)).getBlock() == BlockDefs.flickerLure) {
						BlockPos pos = getPosition().add(x, y, z);
						groundLevel = getTopBlockNearMe();
						targetPosition = new AMVector3(pos.getX() + worldObj.rand.nextInt(5) - 2, groundLevel + worldObj.rand.nextInt(3), pos.getY() + worldObj.rand.nextInt(5) - 2);
						return;
					}
				}		
			}			
		}
		if (aff == Affinity.WATER) {
			for (int i = 0; i < 5; ++i){
				targetPosition = new AMVector3(this.posX - 5 + worldObj.rand.nextInt(10), this.posY - 5 + worldObj.rand.nextInt(10), this.posZ - 5 + worldObj.rand.nextInt(10));
				Block block = worldObj.getBlockState(targetPosition.toBlockPos()).getBlock();
				if (block == Blocks.WATER || block == Blocks.FLOWING_WATER){
					break;
				}
			}
		} else if (aff == Affinity.AIR) {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + worldObj.rand.nextInt(10), groundLevel + 10 + worldObj.rand.nextInt(15), this.posZ - 5 + worldObj.rand.nextInt(10));			
		} else if (aff == Affinity.EARTH) {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + worldObj.rand.nextInt(10), groundLevel + worldObj.rand.nextInt(1) + 1, this.posZ - 5 + worldObj.rand.nextInt(10));			
		} else {
			groundLevel = getTopBlockNearMe();
			targetPosition = new AMVector3(this.posX - 5 + worldObj.rand.nextInt(10), groundLevel + 3 + worldObj.rand.nextInt(5), this.posZ - 5 + worldObj.rand.nextInt(10));
		}
	}

	private int getTopBlockNearMe(){
		
		BlockPos checkPos = this.getPosition();
		
		while (checkPos.getY() > 0 && worldObj.isAirBlock(checkPos))
			checkPos = checkPos.down();
		while (checkPos.getY() < worldObj.getActualHeight() && !worldObj.isAirBlock(checkPos))
			checkPos = checkPos.up();

		return checkPos.getY();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound){
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("flickerType", dataManager.get(WATCHER_FLICKERTYPE));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound){
		super.readEntityFromNBT(par1nbtTagCompound);
		dataManager.set(WATCHER_FLICKERTYPE, par1nbtTagCompound.getInteger("flickerType"));
		ArsMagica2.proxy.incrementFlickerCount();
	}
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand) {
		if (stack != null && stack.getItem() == ItemDefs.flickerJar && !this.isDead){
			if (stack.getItemDamage() == 0){
				if (!worldObj.isRemote){
					setDead();
					InventoryUtilities.decrementStackQuantity(player.inventory, player.inventory.currentItem, 1);
					ItemStack newStack = new ItemStack(ItemDefs.flickerJar);
					ItemDefs.flickerJar.setFlickerJarTypeFromFlicker(newStack, this);
					if (!InventoryUtilities.mergeIntoInventory(player.inventory, newStack)){
						if (!worldObj.isRemote)
							player.dropItem(newStack.getItem(), newStack.getItemDamage());
					}
				}
				return EnumActionResult.SUCCESS;
			}else{
				flick();
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean getCanSpawnHere(){
		if (ArsMagica2.proxy.getTotalFlickerCount() > 12 * worldObj.playerEntities.size() || worldObj.rand.nextDouble() > 0.2f){
			return false;
		}
		//get the biome we're trying to spawn in
		Biome biome = this.getEntityWorld().getBiome(this.getPosition());
		if (biome != null){
			//get the tags on this biome
			Type[] biomeTags = BiomeDictionary.getTypesForBiome(biome);
			//pick a random tag to focus on
			Type tagType = biomeTags[worldObj.rand.nextInt(biomeTags.length)];
			//create a list of valid types based on that tag
			ArrayList<Affinity> validAffinities = new ArrayList<Affinity>();
			//populate the list
			//DO NOT USE THIS LIST FOR AIR/EARTH/LIFE - they are handled by special cases.
			switch (tagType){
			case DEAD:
			case END:
				validAffinities.add(Affinity.ENDER);
				break;
			case PLAINS:
			case FOREST:
			case CONIFEROUS:
			case JUNGLE:
				validAffinities.add(Affinity.NATURE);
				break;
			case COLD:
			case SNOWY:
				validAffinities.add(Affinity.ICE);
				break;
			case MAGICAL:
				validAffinities.add(Affinity.ARCANE);
				break;
			case DRY:
			case HOT:
			case SAVANNA:
			case NETHER:
				validAffinities.add(Affinity.FIRE);
				break;
			case OCEAN:
				validAffinities.add(Affinity.LIGHTNING);
			case SWAMP:
			case WATER:
			case RIVER:
			case WET:
			case BEACH:
				validAffinities.add(Affinity.WATER);
				break;
			case DENSE:
			case LUSH:
			case MESA:
			case SANDY:
			case SPARSE:
			case SPOOKY:
			case WASTELAND:
			case HILLS:
			case MOUNTAIN:
			case MUSHROOM:
			default:
				break;
			}

			//special conditions for air/earth flickers based on y coordinate
			if (posY < 55){
				validAffinities.add(Affinity.EARTH);
			}

			if (worldObj.canBlockSeeSky(getPosition()))
				validAffinities.add(Affinity.AIR);

			if (worldObj.isRaining() && worldObj.rand.nextBoolean()){
				validAffinities.clear();
				validAffinities.add(Affinity.LIGHTNING);
			}

			if (validAffinities.size() <= 0)
				return false;
			
			if (worldObj.provider.getDimension() == 1) {
				validAffinities.clear();
				validAffinities.add(Affinity.ENDER);
			}

			//life flickers always have a chance to spawn?
			MinecraftForge.EVENT_BUS.post(new FlickerAffinityEvent(validAffinities, this, biome));
			if (worldObj.rand.nextDouble() < 0.1f){
				this.setFlickerType(Affinity.LIFE);
			}else{
				this.setFlickerType(validAffinities.get(worldObj.rand.nextInt(validAffinities.size())));
			}

			if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox()) && this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this)){
				ArsMagica2.proxy.incrementFlickerCount();
				return true;
			}
		}
		return false;
	}
}
