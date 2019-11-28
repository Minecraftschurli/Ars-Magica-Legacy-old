package am2.spell.component;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import am2.ArsMagica2;
import am2.api.affinity.Affinity;
import am2.api.blocks.MultiblockStructureDefinition;
import am2.api.rituals.IRitualInteraction;
import am2.api.rituals.RitualShapeHelper;
import am2.api.spell.SpellComponent;
import am2.api.spell.SpellModifiers;
import am2.blocks.tileentity.TileEntityOtherworldAura;
import am2.defs.BlockDefs;
import am2.defs.ItemDefs;
import am2.items.ItemOre;
import am2.particles.AMParticle;
import am2.particles.ParticleArcToPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Transplace extends SpellComponent implements IRitualInteraction{

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, EnumFacing blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		Block block = world.getBlockState(blockPos).getBlock();
		if (!world.isRemote && caster instanceof EntityPlayer && block == BlockDefs.inertSpawner){
			if (RitualShapeHelper.instance.matchesRitual(this, world, blockPos)){
				RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
				RitualShapeHelper.instance.consumeShape(this, world, blockPos);
				world.setBlockState(blockPos, BlockDefs.otherworldAura.getDefaultState());
				TileEntity te = world.getTileEntity(blockPos);
				if (te != null && te instanceof TileEntityOtherworldAura){
					((TileEntityOtherworldAura)te).setPlacedByUsername(((EntityPlayer)caster).getName());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		if (!world.isRemote && target != null && !target.isDead){
			double tPosX = target.posX;
			double tPosY = target.posY;
			double tPosZ = target.posZ;

			double cPosX = caster.posX;
			double cPosY = caster.posY;
			double cPosZ = caster.posZ;

			caster.setPositionAndUpdate(tPosX, tPosY, tPosZ);
			if (target instanceof EntityLiving)
				((EntityLiving)target).setPositionAndUpdate(cPosX, cPosY, cPosZ);
			else
				target.setPosition(cPosX, cPosY, cPosZ);

		}
		if (target instanceof EntityLiving)
			((EntityLiving)target).faceEntity(caster, 180f, 180f);
		return true;
	}
	
	@Override
	public EnumSet<SpellModifiers> getModifiers() {
		return EnumSet.noneOf(SpellModifiers.class);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 100;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < 15; ++i){
			AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ);
			if (particle != null){
				particle.addRandomOffset(1, 1, 1);
				particle.AddParticleController(new ParticleArcToPoint(particle, 1, target.posX, target.posY + target.getEyeHeight(), target.posZ, false).SetSpeed(0.05f).generateControlPoints());
				particle.setMaxAge(40);
				particle.setParticleScale(0.2f);
				particle.setRGBColorF(1, 0, 0);
				if (colorModifier > -1){
					particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
				}
			}
		}

		for (int i = 0; i < 15; ++i){
			AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", target.posX, target.posY + target.getEyeHeight(), target.posZ);
			if (particle != null){
				particle.addRandomOffset(1, 1, 1);
				particle.AddParticleController(new ParticleArcToPoint(particle, 1, caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ, false).SetSpeed(0.05f).generateControlPoints());
				particle.setMaxAge(40);
				particle.setParticleScale(0.2f);
				particle.setRGBColorF(0, 0, 1);
				if (colorModifier > -1){
					particle.setRGBColorF((0xFF - ((colorModifier >> 16) & 0xFF)) / 255.0f, (0xFF - ((colorModifier >> 8) & 0xFF)) / 255.0f, (0xFF - (colorModifier & 0xFF)) / 255.0f);
				}
			}
		}
	}

	@Override
	public Set<Affinity> getAffinity(){
		return Sets.newHashSet(Affinity.ENDER);
	}

	@Override
	public Object[] getRecipe(){
		return new Object[]{
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.RED.getDyeDamage()),
				Items.COMPASS,
				new ItemStack(ItemDefs.rune, 1, EnumDyeColor.BLUE.getDyeDamage()),
				Items.ENDER_PEARL
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.02f;
	}

	@Override
	public MultiblockStructureDefinition getRitualShape(){
		return RitualShapeHelper.instance.ringedCross;
	}

	@Override
	public ItemStack[] getReagents(){
		return new ItemStack[]{
				new ItemStack(ItemDefs.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
				new ItemStack(ItemDefs.mageArmor),
				new ItemStack(ItemDefs.mageBoots),
				new ItemStack(ItemDefs.mageHood),
				new ItemStack(ItemDefs.mageLeggings),
				new ItemStack(ItemDefs.playerFocus)
		};
	}

	@Override
	public int getReagentSearchRadius(){
		return 3;
	}

	@Override
	public void encodeBasicData(NBTTagCompound tag, Object[] recipe) {}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getResult() {
		return new ItemStack(BlockDefs.otherworldAura);
	}
}
