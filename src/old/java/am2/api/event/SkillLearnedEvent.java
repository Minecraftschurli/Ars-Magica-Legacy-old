package am2.api.event;

import am2.api.skill.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;

public class SkillLearnedEvent extends PlayerEvent{
	
	protected final Skill skill;

	public SkillLearnedEvent(EntityPlayer player, Skill skill) {
		super(player);
		this.skill = skill;
	}
	
	public Skill getSkill() {
		return skill;
	}

}
