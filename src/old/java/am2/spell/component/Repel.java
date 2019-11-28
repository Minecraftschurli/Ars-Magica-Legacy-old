package am2.spell.component;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import am2.ArsMagica2;
import am2.api.affinity.Affinity;
import am2.api.math.AMVector3;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifiers;
import am2.defs.ItemDefs;
import am2.packet.AMNetHandler;
import am2.particles.AMParticle;
import am2.particles.ParticleFadeOut;
import am2.particles.ParticleFleePoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Repel extends SpellComponent{

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		if (target == null)
			return false;
		if (target == caster){
			EntityLivingBase source = caster;

			if (target instanceof EntityLivingBase)
				source = (EntityLivingBase)target;

			List<Entity> ents = world.getEntitiesWithinAABB(Entity.class, source.getEntityBoundingBox().expand(2, 2, 2));

			for (Entity e : ents){
				performRepel(world, caster, e);
			}
			return true;
		}

		performRepel(world, caster, target);

		return true;
	}

	private void performRepel(World world, EntityLivingBase caster, Entity target){
		Vec3d casterPos = new Vec3d(caster.posX, caster.posY, caster.posZ);
		Vec3d targetPos = new Vec3d(target.posX, target.posY, target.posZ);
		double distance = casterPos.distanceTo(targetPos) + 0.1D;

		Vec3d delta = new Vec3d(targetPos.xCoord - casterPos.xCoord, targetPos.yCoord - casterPos.yCoord, targetPos.zCoord - casterPos.zCoord);

		double dX = delta.xCoord / 2.5D / distance;
		double dY = delta.yCoord / 2.5D / distance;
		double dZ = delta.zCoord / 2.5D / distance;
		if (target instanceof EntityPlayer){
			AMNetHandler.INSTANCE.sendVelocityAddPacket(world, (EntityPlayer)target, dX, dY, dZ);
		}
		target.motionX += dX;
		target.motionY += dY;
		target.motionZ += dZ;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}
	
	@Override
	public float manaCost(EntityLivingBase caster){
		return 5.0f;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < ArsMagica2.config.getGFXLevel() * 2; i++) {
			AMParticle effect = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "sparkle", x, y, z);
			if (effect != null){
				effect.addRandomOffset(1, 2, 1);
				//double dx = caster.posX - target.posX;
				//double dz = caster.posZ - target.posZ;
				//double angle = Math.toDegrees(Math.atan2(-dz, -dx));
				//effect.AddParticleController(new ParticleMoveOnHeading(effect, angle, 0, 0.1 + rand.nextDouble() * 0.5, 1, false));
				effect.AddParticleController(new ParticleFleePoint(effect, new AMVector3(caster).add(new AMVector3(0, caster.getEyeHeight(), 0)).toVec3D(), 0.075f, 3f, 1, true));
				effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f));
				effect.setMaxAge(20);
				if (colorModifier > -1){
					effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}
	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.NONE);
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.WHITE.getDyeDamage()),
				Items.WATER_BUCKET
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
}
