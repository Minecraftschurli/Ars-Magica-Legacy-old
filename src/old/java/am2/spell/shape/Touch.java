package am2.spell.shape;

import java.util.EnumSet;

import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.ItemDefs;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.spell.SpellCastResult;
import am2.utils.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Touch extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){
		if (target != null){
			Entity e = target;
			if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
				e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;

			SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
			return result;
		}
		
		boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
		RayTraceResult mop = item.getMovingObjectPosition(caster, world, 2.5f, true, targetWater);
		if (mop == null){
			return SpellCastResult.EFFECT_FAILED;
		}else{
			if (mop.typeOfHit == RayTraceResult.Type.ENTITY){
				Entity e = mop.entityHit;
				if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
					e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;
				SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, (target == null) ? e : target, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
				return SpellUtils.applyStackStage(stack, caster, target, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, null, world, true, giveXP, 0);
			}else{
				SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, mop.getBlockPos(), mop.sideHit, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, giveXP);
				if (result != SpellCastResult.SUCCESS){
					return result;
				}
				return SpellUtils.applyStackStage(stack, caster, target, mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ(), mop.sideHit, world, true, giveXP, 0);
			}
		}
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.TARGET_NONSOLID_BLOCKS);
	}


	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_VINTEUM),
				Items.FEATHER,
				Items.FISH,
				Items.CLAY_BALL
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1;
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
