package am2.items;

import java.util.List;

import com.google.common.collect.Multimap;

import am2.ArsMagica2;
import am2.buffs.BuffEffectFrostSlowed;
import am2.defs.ItemDefs;
import am2.entity.EntityWinterGuardianArm;
import am2.extensions.EntityExtension;
import am2.particles.AMParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemWinterGuardianArm extends ItemArsMagica{

	public ItemWinterGuardianArm(){
		super();
		setMaxStackSize(1);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot){
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
		if (slot.equals(EntityEquipmentSlot.MAINHAND)) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1, 0));
		}
		return multimap;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4){
		par3List.add(I18n.translateToLocal("am2.tooltip.winter_arm"));
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if (entity instanceof EntityLivingBase){
			((EntityLivingBase)entity).addPotionEffect(new BuffEffectFrostSlowed(60, 3));
			if (player.worldObj.isRemote){
				for (int i = 0; i < 5; ++i){
					AMParticle particle = (AMParticle)ArsMagica2.proxy.particleManager.spawn(player.worldObj, "snowflakes", entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5);
					if (particle != null){
						particle.addRandomOffset(1, 0.5, 1);
						particle.addVelocity(player.worldObj.rand.nextDouble() * 0.2 - 0.1, 0.3, player.worldObj.rand.nextDouble() * 0.2 - 0.1);
						particle.setAffectedByGravity();
						particle.setDontRequireControllers();
						particle.setMaxAge(10);
						particle.setParticleScale(0.1f);
					}
				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand){
		if (flingArm(par1ItemStack, par2World, par3EntityPlayer)){
			par3EntityPlayer.setItemStackToSlot(hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, null);//inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, par1ItemStack);
	}

	public boolean flingArm(ItemStack stack, World world, EntityPlayer player){
		if (!EntityExtension.For(player).hasEnoughtMana(250) && !player.capabilities.isCreativeMode){
			if (world.isRemote)
				ArsMagica2.proxy.flashManaBar();
			return false;
		}
		if (!world.isRemote){
			EntityWinterGuardianArm projectile = new EntityWinterGuardianArm(world, player, 1.25f);
			projectile.setThrowingEntity(player);
			projectile.setProjectileSpeed(2.0);
			world.spawnEntityInWorld(projectile);
		}
		EntityExtension.For(player).deductMana(250f);
		return true;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List){
		par3List.add(ItemDefs.winterArmEnchanted.copy());
	}
}
