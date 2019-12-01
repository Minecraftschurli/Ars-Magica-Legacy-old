package am2.api.event;

import am2.api.skill.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

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
