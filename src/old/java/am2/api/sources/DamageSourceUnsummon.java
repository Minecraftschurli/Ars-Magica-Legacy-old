package am2.api.sources;

import net.minecraft.util.DamageSource;

public class DamageSourceUnsummon extends DamageSource{

	public DamageSourceUnsummon(){
		super("am2.backfire");
		this.setDamageAllowedInCreativeMode();
	}
}
