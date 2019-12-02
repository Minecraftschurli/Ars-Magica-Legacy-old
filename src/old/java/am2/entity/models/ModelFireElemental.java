package am2.entity.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFireElemental extends ModelBiped{
	public ModelFireElemental(){
		super();
		this.rightArmPose = ArmPose.ITEM;
	}
}
