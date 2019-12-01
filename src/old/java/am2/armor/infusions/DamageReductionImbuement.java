package am2.armor.infusions;

import java.util.EnumSet;

import am2.api.items.armor.ArmorImbuement;
import am2.api.items.armor.ImbuementApplicationTypes;
import am2.api.items.armor.ImbuementTiers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamageReductionImbuement extends ArmorImbuement{

	private String id = "";
	private String dmgType = "";
	private ImbuementTiers tier;
	EntityEquipmentSlot[] allArmor = new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD};

	private DamageReductionImbuement(String id, String dmgType, ImbuementTiers tier){
		this.id = id;
		this.dmgType = dmgType;
		this.tier = tier;
	}

	public static void registerAll(){

		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_phy", "generic", ImbuementTiers.SECOND));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_drn", "drown", ImbuementTiers.SECOND));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_fall", "fall", ImbuementTiers.SECOND));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_exp", "explosion", ImbuementTiers.SECOND));

		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_fire", "fire", ImbuementTiers.THIRD));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_frst", "frost", ImbuementTiers.THIRD));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_mage", "magic", ImbuementTiers.THIRD));
		ImbuementRegistry.instance.registerImbuement(new DamageReductionImbuement("dr_litn", "lightning", ImbuementTiers.THIRD));
	}

	@Override
	public String getID(){
		return id;
	}
	
	@Override
	public ImbuementTiers getTier(){
		return tier;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_HIT);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){
		LivingHurtEvent event = (LivingHurtEvent)params[0];
		if (event.getSource().damageType.equals(dmgType) ||
				(dmgType.equals("fire") && event.getSource().isFireDamage()) ||
				(dmgType.equals("magic") && event.getSource().isMagicDamage()) ||
				(dmgType.equals("explosion") && event.getSource().isExplosion())
				){
			event.setAmount(event.getAmount() * 0.85f);
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return allArmor;
	}

	@Override
	public boolean canApplyOnCooldown(){
		return true;
	}

	@Override
	public int getCooldown(){
		return 0;
	}


	@Override
	public int getArmorDamage(){
		return 0;
	}
}
