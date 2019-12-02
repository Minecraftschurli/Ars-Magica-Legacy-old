package am2.entity.ai.selectors;

import com.google.common.base.Predicate;

import am2.entity.EntityDarkMage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class DarkMageEntitySelector implements Predicate<EntityLivingBase>{

	public static final DarkMageEntitySelector instance = new DarkMageEntitySelector();

	private DarkMageEntitySelector(){
	}

	@Override
	public boolean apply(EntityLivingBase entity){
		if (entity instanceof EntityDarkMage || (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD))
			return false;
		return true;
	}

}
