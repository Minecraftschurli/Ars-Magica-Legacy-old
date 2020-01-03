package am2.api.event;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SpellCastEvent extends Event {
	
	public ItemStack spell;
	public float manaCost;
	public EntityLivingBase entityLiving;
	public float burnout;
	
	public SpellCastEvent(EntityLivingBase caster, ItemStack spell, float manaCost) {
		this.spell = spell;
		this.manaCost = manaCost;
		this.entityLiving = caster;
	}
	
	public static class Pre extends SpellCastEvent {


		public Pre(EntityLivingBase caster, ItemStack spell, float manaCost) {
			super(caster, spell, manaCost);
		}
		
	}
	
	public static class Post extends SpellCastEvent {

		public Post(EntityLivingBase caster, ItemStack spell, float manaCost) {
			super(caster, spell, manaCost);
		}
		
	}

}
