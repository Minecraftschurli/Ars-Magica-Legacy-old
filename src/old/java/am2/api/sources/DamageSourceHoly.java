package am2.api.sources;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class DamageSourceHoly extends EntityDamageSource{
	public DamageSourceHoly(EntityLivingBase source){
		super("am2.holy", source);
		this.setDamageBypassesArmor();
	}
}
