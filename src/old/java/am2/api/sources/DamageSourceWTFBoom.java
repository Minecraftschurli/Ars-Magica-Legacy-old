package am2.api.sources;

import net.minecraft.util.DamageSource;

public class DamageSourceWTFBoom extends DamageSource{

	public DamageSourceWTFBoom(){
		super("am2.wtfboom");
		this.setDamageAllowedInCreativeMode();
		this.setDamageBypassesArmor();
	}
}
