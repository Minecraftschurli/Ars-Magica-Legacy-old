package am2.buffs;

import am2.api.extensions.*;
import am2.defs.*;
import am2.extensions.*;
import net.minecraft.entity.*;

public class ManaPotion extends AMPotion{
	
	int baseRegen;

	protected ManaPotion(boolean par2, int par3, int baseRegen){
		super(par2, par3);
		this.baseRegen = baseRegen;
	}

	@Override
	public boolean isInstant(){
		return true;
	}

	public boolean isReady(int par1, int par2){
		return par1 >= 1;
	}

	private int getManaRestored(int amplifier){
		int manaRestored = baseRegen;
		
		for (int i = 0; i < amplifier; i++) {
			manaRestored *= 1.25;
		}

		return manaRestored;
	}
	
	
	@Override
	public void affectEntity(Entity par1EntityLiving, Entity ent, EntityLivingBase par2EntityLiving, int amplifier, double distanceToImpact){
		int manaRestored = getManaRestored(amplifier);
		IEntityExtension ext = par1EntityLiving.getCapability(EntityExtension.INSTANCE, null);
		ext.setCurrentMana(ext.getCurrentMana() + manaRestored * (int)(1/distanceToImpact));
	}

	@Override
	public void performEffect(EntityLivingBase par1EntityLiving, int amplifier){
		int manaRestored = getManaRestored(amplifier);
		IEntityExtension ext = par1EntityLiving.getCapability(EntityExtension.INSTANCE, null);
		ext.setCurrentMana(ext.getCurrentMana() + manaRestored);
	}
}
