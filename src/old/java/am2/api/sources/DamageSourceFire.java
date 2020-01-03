package am2.api.sources;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class DamageSourceFire extends EntityDamageSource{
	public DamageSourceFire(EntityLivingBase source){
		super("am2.fire", source);
		this.setFireDamage();
		this.setDamageBypassesArmor();
	}
}
