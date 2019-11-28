package am2.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import am2.ArsMagica2;
import am2.api.affinity.Affinity;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifiers;
import am2.defs.ItemDefs;
import am2.particles.AMParticle;
import am2.utils.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Ignition extends SpellComponent{

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){

		switch (blockFace){
		case EAST:
		case WEST:
		case SOUTH:
		case NORTH:
			pos = pos.offset(blockFace);
			break;
		case DOWN:
		case UP:
		default:
			break;
		}

		Block block = world.getBlockState(pos).getBlock();

		if (world.isAirBlock(pos) || block.equals(Blocks.SNOW) || block instanceof BlockFlower){
			if (!world.isRemote) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
			return true;
		}else{
			pos = pos.up();
			block = world.getBlockState(pos).getBlock();
			if (world.isAirBlock(pos) || block.equals(Blocks.SNOW) || block instanceof BlockFlower){
				if (!world.isRemote) world.setBlockState(pos, Blocks.FIRE.getDefaultState());
				return true;
			}
		}
		return false;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.DURATION);
	}


	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		int burnTime = SpellUtils.getModifiedInt_Mul(3, stack, caster, target, world, SpellModifiers.DURATION);
		//burnTime = SpellUtils.modifyDurationBasedOnArmor(caster, burnTime);
		if (target.isBurning()){
			return false;
		}
		target.setFire(burnTime);
		return true;
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 35;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < 5; ++i){
			AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "explosion_2", x + 0.5, y + 0.5, z + 0.5);
			if (particle != null){
				particle.addRandomOffset(1, 0.5, 1);
				particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.3, rand.nextDouble() * 0.2 - 0.1);
				particle.setAffectedByGravity();
				particle.setDontRequireControllers();
				particle.setMaxAge(5);
				particle.setParticleScale(0.1f);
				if (colorModifier > -1){
					particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}
	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.FIRE);
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.ORANGE.getDyeDamage()),
				Items.FLINT_AND_STEEL
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.01f;
	}

}
