package am2.api.event;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderingItemEvent extends Event {
	
	private final ItemStack stack;
	private final TransformType cameraTransformType;
	private final EntityLivingBase entity;
	
	public RenderingItemEvent(ItemStack stack, TransformType cameraTransformType, EntityLivingBase entity) {
		this.stack = stack;
		this.cameraTransformType = cameraTransformType;
		this.entity = entity;
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public TransformType getCameraTransformType() {
		return cameraTransformType;
	}
	
	public EntityLivingBase getEntity() {
		return entity;
	}
}
