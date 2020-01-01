package am2.api.sources;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class DamageSourceFrost extends EntityDamageSource{
	public DamageSourceFrost(EntityLivingBase source){
		super("am2.frost", source);
		this.setDamageBypassesArmor();
	}
}
