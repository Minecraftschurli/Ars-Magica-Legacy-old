package am2.spell.shape;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import am2.ArsMagica2;
import am2.api.affinity.Affinity;
import am2.api.spell.SpellModifier;
import am2.api.spell.SpellModifiers;
import am2.api.spell.SpellShape;
import am2.defs.ItemDefs;
import am2.items.ItemOre;
import am2.items.ItemSpellBase;
import am2.spell.SpellCastResult;
import am2.spell.modifier.Colour;
import am2.utils.AffinityShiftUtils;
import am2.utils.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Chain extends SpellShape{

	@Override
	public SpellCastResult beginStackStage(ItemSpellBase item, ItemStack stack, EntityLivingBase caster, EntityLivingBase target, World world, double x, double y, double z, EnumFacing side, boolean giveXP, int useCount){

		RayTraceResult mop = item.getMovingObjectPosition(caster, world, 8.0f, true, false);
		double range = SpellUtils.getModifiedDouble_Mul(8, stack, caster, target, world, SpellModifiers.RANGE);
		int num_targets = SpellUtils.getModifiedInt_Add(3, stack, caster, target, world, SpellModifiers.PROCS);

		ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();

		if (target != null){
			mop = new RayTraceResult(target);
		}

		if (mop != null && mop.typeOfHit == RayTraceResult.Type.ENTITY && mop.entityHit != null){
			Entity e = mop.entityHit;
			if (e instanceof EntityDragonPart && ((EntityDragonPart)e).entityDragonObj instanceof EntityLivingBase)
				e = (EntityLivingBase)((EntityDragonPart)e).entityDragonObj;
			if (e instanceof EntityLivingBase){
				do{
					targets.add((EntityLivingBase)e);

					List<EntityLivingBase> nearby = world.getEntitiesWithinAABB(EntityLivingBase.class, e.getEntityBoundingBox().expand(range, range, range));
					EntityLivingBase closest = null;
					for (EntityLivingBase near : nearby){
						if (targets.contains(near) || near == caster) continue;

						if (closest == null || closest.getDistanceSqToEntity(e) > near.getDistanceSqToEntity(e)){
							closest = near;
						}
					}

					e = closest;

				}while (e != null && targets.size() < num_targets);
			}
		}

		boolean atLeastOneApplication = false;
		SpellCastResult result = SpellCastResult.SUCCESS;

		EntityLivingBase prevEntity = null;

		for (EntityLivingBase e : targets){
			if (e == caster)
				continue;
			result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
			SpellUtils.applyStackStage(stack, caster, e, e.posX, e.posY, e.posZ, null, world, true, giveXP, 0);

			if (world.isRemote){
				if (prevEntity == null)
					spawnChainParticles(world, x, y, z, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
				else
					spawnChainParticles(world, prevEntity.posX, prevEntity.posY + e.getEyeHeight(), prevEntity.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
			}
			prevEntity = e;

			if (result == SpellCastResult.SUCCESS){
				atLeastOneApplication = true;
			}
		}

		if (atLeastOneApplication){
			return SpellCastResult.SUCCESS;
		}
		return result;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.PROCS);
	}


	private void spawnChainParticles(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, ItemStack spellStack){
		int color = getPFXColor(spellStack);

		Affinity aff = AffinityShiftUtils.getMainShiftForStack(spellStack);

		if (aff.equals(Affinity.LIGHTNING)){
			ArsMagica2.proxy.particleManager.BoltFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 1, color);
		}else{
			if (color == -1)
				color = aff.getColor();
			ArsMagica2.proxy.particleManager.BeamFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, color);
		}
	}

	private int getPFXColor(ItemStack stack){
		int color = -1;
		if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, stack)){
			ArrayList<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
			for (SpellModifier mod : mods){
				if (mod instanceof Colour){
					color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTagCompound());
				}
			}
		}
		return color;
	}

	@Override
	public boolean isChanneled(){
		return false;
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_SUNSTONE),
				Items.LEAD,
				Items.IRON_INGOT,
				Blocks.TRIPWIRE_HOOK,
				Items.STRING
		};
	}

	@Override
	public float manaCostMultiplier(ItemStack spellStack){
		return 1.5f;
	}

	@Override
	public boolean isTerminusShape(){
		return false;
	}

	@Override
	public boolean isPrincipumShape(){
		return false;
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}

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
}
