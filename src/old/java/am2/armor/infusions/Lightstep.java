package am2.armor.infusions;

import am2.api.items.armor.*;
import am2.defs.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Lightstep extends ArmorImbuement{

	@Override
	public String getID(){
		return "lightstep";
	}

	@Override
	public ImbuementTiers getTier(){
		return ImbuementTiers.FOURTH;
	}

	@Override
	public EnumSet<ImbuementApplicationTypes> getApplicationTypes(){
		return EnumSet.of(ImbuementApplicationTypes.ON_TICK);
	}

	@Override
	public boolean applyEffect(EntityPlayer player, World world, ItemStack stack, ImbuementApplicationTypes matchedType, Object... params){

		if (world.isRemote)
			return false;

		if (player.isSneaking())
			return false;
		BlockPos pos = player.getPosition().up();
		int ll = world.getLightFor(EnumSkyBlock.BLOCK, pos);
		if (ll < 7 && world.isAirBlock(pos)){
			world.setBlockState(pos, BlockDefs.blockMageLight.getDefaultState(), 2);
			return true;
		}
		return false;
	}

	@Override
	public EntityEquipmentSlot[] getValidSlots(){
		return new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET};
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
		return 1;
	}
}
