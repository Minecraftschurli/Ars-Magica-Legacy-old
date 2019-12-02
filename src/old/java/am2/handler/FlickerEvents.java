package am2.handler;

import am2.api.affinity.Affinity;
import am2.entity.EntityFlicker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FlickerEvents {
	
	@SubscribeEvent
	public void endermanTeleport(EnderTeleportEvent event) {
		EntityLivingBase ent = event.getEntityLiving();
		
		if (!ent.worldObj.isRemote && ent instanceof EntityEnderman && ent.worldObj.rand.nextDouble() < 0.01f){
			EntityFlicker flicker = new EntityFlicker(ent.worldObj);
			flicker.setPosition(ent.posX, ent.posY, ent.posZ);
			flicker.setFlickerType(Affinity.ENDER);
			ent.worldObj.spawnEntityInWorld(flicker);
		}
	}
	
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event){
		if (!event.getWorld().isRemote){
			for (ClassInheritanceMultiMap<Entity> l : event.getChunk().getEntityLists()){
				for (Object o : l){
					if (o instanceof EntityFlicker){
						((EntityFlicker)o).setDead();
					}
				}
			}
		}
	}
}
