package am2.entity;

import am2.defs.AMSounds;
import am2.defs.ItemDefs;
import am2.defs.LootTablesArsMagica;
import am2.entity.ai.EntityAIManaDrainBolt;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityHellCow extends EntityMob{

	public EntityHellCow(World par1World){
		super(par1World);
		this.setSize(1.0f, 2.5f);
		initAI();
	}

	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50D);
	}

	@Override
	public int getTotalArmorValue(){
		return 15;
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return LootTablesArsMagica.HELL_COW_LOOT;
	}
	
	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
		super.dropEquipment(wasRecentlyHit, lootingModifier);
		int i = rand.nextInt(100);
		if (i == 1 && wasRecentlyHit){
			this.entityDropItem(ItemDefs.hellCowHorn.createItemStack(), 0.0f);
		}
	}

	@Override
	protected SoundEvent getHurtSound(){
		return AMSounds.MOO_HIT;
	}

	@Override
	protected SoundEvent getDeathSound(){
		return AMSounds.MOO_DEATH;
	}
	
	@Override
	protected SoundEvent getAmbientSound(){
		return AMSounds.MOO_IDLE;
	}

	private void initAI(){
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.6F, false));
		this.tasks.addTask(4, new EntityAIManaDrainBolt(this, 0.5f, 35, 2, 0));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0F));
		this.tasks.addTask(7, new EntityAIWander(this, 0.3F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 0, false, false, null));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2){
		if (par2 > 10) par2 = 10;
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public boolean getCanSpawnHere(){
		return true;
	}


	@Override
	public int getTalkInterval(){
		return 160;
	}

	@Override
	protected float getSoundVolume(){
		return 0.4f;
	}
}
