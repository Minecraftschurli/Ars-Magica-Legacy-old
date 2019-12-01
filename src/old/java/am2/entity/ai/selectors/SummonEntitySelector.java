package am2.entity.ai.selectors;

import com.google.common.base.Predicate;

import am2.utils.EntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class SummonEntitySelector implements Predicate<EntityMob>{

	public static final SummonEntitySelector instance = new SummonEntitySelector();

	private SummonEntitySelector(){
	}
	@Override
	public boolean apply(EntityMob entity) {
		if (entity instanceof EntityLivingBase){
			if (EntityUtils.isSummon((EntityLivingBase)entity))
				return false;
			return true;
		}
		return false;
	}

}
