package am2.api.sources;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class DamageSourceLightning extends EntityDamageSource{
	public DamageSourceLightning(EntityLivingBase source){
		super("am2.lightning", source);
		this.setDamageBypassesArmor();
	}
}
