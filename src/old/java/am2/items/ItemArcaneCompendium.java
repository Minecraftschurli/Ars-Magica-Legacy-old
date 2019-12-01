package am2.items;

import am2.gui.AMGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemArcaneCompendium extends ItemArsMagica{

	public ItemArcaneCompendium(){
		super();
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (worldIn.isRemote){
			AMGuiHelper.OpenCompendiumGui(itemStackIn);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
