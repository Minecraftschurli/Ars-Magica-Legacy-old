package am2.spell.shape;

import java.util.EnumSet;

import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.BlockDefs;
import am2.defs.ItemDefs;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.power.PowerTypes;
import am2.spell.SpellCastResult;
import am2.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Self extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, caster, giveXP);
		if (result != SpellCastResult.SUCCESS){
			return result;
		}

		return SpellUtils.applyStackStage(stack, caster, target, x, y, z, null, world, true, giveXP, 0);
	}

	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				BlockDefs.aum,
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				ItemDefs.lesserFocus,
				"E:" + PowerTypes.NEUTRAL.ID(), 500
		};
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 0.5f;
	}

	@Override
	public boolean isTerminusShape(){
		return false;
	}

	@Override
	public boolean isPrincipumShape(){
		return false;
	}

//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		switch (affinity){
//		case AIR:
//			return "arsmagica2:spell.cast.air";
//		case ARCANE:
//			return "arsmagica2:spell.cast.arcane";
//		case EARTH:
//			return "arsmagica2:spell.cast.earth";
//		case ENDER:
//			return "arsmagica2:spell.cast.ender";
//		case FIRE:
//			return "arsmagica2:spell.cast.fire";
//		case ICE:
//			return "arsmagica2:spell.cast.ice";
//		case LIFE:
//			return "arsmagica2:spell.cast.life";
//		case LIGHTNING:
//			return "arsmagica2:spell.cast.lightning";
//		case NATURE:
//			return "arsmagica2:spell.cast.nature";
//		case WATER:
//			return "arsmagica2:spell.cast.water";
//		case NONE:
//		default:
//			return "arsmagica2:spell.cast.none";
//		}
//	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
