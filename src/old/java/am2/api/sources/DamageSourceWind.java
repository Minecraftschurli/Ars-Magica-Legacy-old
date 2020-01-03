package am2.api.sources;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class DamageSourceWind extends EntityDamageSource{
	public DamageSourceWind(EntityLivingBase source){
		super("am2.wind", source);
		this.setDamageBypassesArmor();
	}
}
