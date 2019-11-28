package am2.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import am2.api.affinity.Affinity;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifiers;
import am2.defs.ItemDefs;
import am2.utils.DummyEntityPlayer;
import am2.utils.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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

public class WizardsAutumn extends SpellComponent{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				Blocks.SAPLING,
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.GREEN.getDyeDamage()),
				Items.STICK,
				Items.IRON_INGOT
		};
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, EnumFacing blockFace,
			double impactX, double impactY, double impactZ, EntityLivingBase caster){

		if (!world.isRemote){
			int radius = 2;
			radius = SpellUtils.getModifiedInt_Mul(radius, stack, caster, null, world, SpellModifiers.RADIUS);
			for (int i = -radius; i <= radius; ++i){
				for (int j = -radius; j <= radius; ++j){
					for (int k = -radius; k <= radius; ++k){
						BlockPos pos = blockPos.add(i, j, k);
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						if (block != null && block.isLeaves(state, world, pos)){
							if (block.removedByPlayer(state, world, pos, DummyEntityPlayer.fromEntityLiving(caster), true)){
								block.onBlockDestroyedByPlayer(world, pos, state);
								block.harvestBlock(world, DummyEntityPlayer.fromEntityLiving(caster), pos, state, null, stack);
								//TODO : play sound
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return applyEffectBlock(stack, world, target.getPosition(), null, target.posX, target.posY, target.posZ, caster);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 15;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.NATURE);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.01f;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.of(SpellModifiers.RADIUS);
	}
}
