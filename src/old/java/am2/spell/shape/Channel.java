package am2.spell.shape;

import java.util.EnumSet;

import am2.api.affinity.Affinity;
import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.BlockDefs;
import am2.defs.ItemDefs;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.spell.SpellCastResult;
import am2.spell.component.Attract;
import am2.spell.component.Repel;
import am2.spell.component.Telekinesis;
import am2.utils.AffinityShiftUtils;
import am2.utils.SpellUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Channel extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		boolean shouldApplyEffect = useCount % 10 == 0 || SpellUtils.componentIsPresent(stack, Telekinesis.class) || SpellUtils.componentIsPresent(stack, Attract.class) || SpellUtils.componentIsPresent(stack, Repel.class);
		if (shouldApplyEffect){
			SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, caster, giveXP);
			if (result != SpellCastResult.SUCCESS){
				return result;
			}
		}

		return SpellCastResult.SUCCESS;
	}

	@Override
	public boolean isChanneled(){
		return true;
	}

	@Override
	public Object[] getRecipe(){
		//Arcane Ash, Arcane Essence, Tarma Root, 500 any power
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_ARCANEASH),
				AffinityShiftUtils.getEssenceForAffinity(Affinity.ARCANE),
				BlockDefs.tarmaRoot,
				"E:*", 500
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1;
	}

	@Override
	public boolean isTerminusShape(){
		return true;
	}

	@Override
	public boolean isPrincipumShape(){
		return false;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
	
//	@Override
//	public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//		switch (affinity){
//		case AIR:
//			return "arsmagica2:spell.loop.air";
//		case ARCANE:
//			return "arsmagica2:spell.loop.arcane";
//		case EARTH:
//			return "arsmagica2:spell.loop.earth";
//		case ENDER:
//			return "arsmagica2:spell.loop.ender";
//		case FIRE:
//			return "arsmagica2:spell.loop.fire";
//		case ICE:
//			return "arsmagica2:spell.loop.ice";
//		case LIFE:
//			return "arsmagica2:spell.loop.life";
//		case LIGHTNING:
//			return "arsmagica2:spell.loop.lightning";
//		case NATURE:
//			return "arsmagica2:spell.loop.nature";
//		case WATER:
//			return "arsmagica2:spell.loop.water";
//		case NONE:
//		default:
//			return "arsmagica2:spell.loop.none";
//		}
//	}
}
