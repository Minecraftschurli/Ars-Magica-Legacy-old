package am2.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import am2.api.affinity.Affinity;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifiers;
import am2.defs.ItemDefs;
import am2.items.ItemCore;
import am2.items.ItemOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Moonrise extends SpellComponent{

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_MOONSTONE),
				Items.CLOCK,
				new ItemStack(ItemDefs.core, 1, ItemCore.META_PURE)
		};
	}

	private boolean setNightTime(World world){
		if (!world.isDaytime())
			return false;
		if (!world.isRemote){
			long curTime = ((WorldServer)world).getWorldTime();
			int day = (int)Math.floor(curTime / 24000);
			((WorldServer)world).setWorldTime((day * 24000) + 13250);
		}
		return true;
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return setNightTime(world);
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return setNightTime(world);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 25000;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.NONE);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0;
	}
	
	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}

}
