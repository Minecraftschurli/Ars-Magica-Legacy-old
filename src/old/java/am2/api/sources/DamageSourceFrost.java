package am2.api.sources;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceFrost extends EntityDamageSource{
	public DamageSourceFrost(EntityLivingBase source){
		super("am2.frost", source);
		this.setDamageBypassesArmor();
	}
}
