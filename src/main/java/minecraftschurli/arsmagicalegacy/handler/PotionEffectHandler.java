package minecraftschurli.arsmagicalegacy.handler;

import minecraftschurli.arsmagicalegacy.event.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.effect.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.*;
import net.minecraftforge.eventbus.api.*;


public class PotionEffectHandler {
	
	@SubscribeEvent(priority= EventPriority.HIGHEST)
	public static void playerPreDeathEvent(LivingDeathEvent e) {
		EffectInstance effect = e.getEntityLiving().getActivePotionEffect(ModEffects.TEMPORAL_ANCHOR.get());
		if (effect != null) {
			((AMEffect)effect.getPotion()).stopEffect(e.getEntityLiving());
			e.getEntityLiving().removePotionEffect(ModEffects.TEMPORAL_ANCHOR.get());
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void addPotionEffect(PotionEvent.PotionAddedEvent e) {
		if (e.getPotionEffect().getPotion() instanceof AMEffect)
			((AMEffect)e.getPotionEffect().getPotion()).startEffect(e.getEntityLiving());
	}


	
	@SubscribeEvent
	public static void removePotionEffect(PotionEvent.PotionRemoveEvent e) {
		if (e.getPotion() instanceof AMEffect)
			((AMEffect)e.getPotion()).stopEffect(e.getEntityLiving());
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public static void entityDamageEvent(LivingHurtEvent event) {
		if (event.isCanceled()) return;
		
		if (event.getSource().damageType.equals(DamageSource.OUT_OF_WORLD.damageType)) return;
		
		if (event.getEntityLiving().isPotionActive(ModEffects.MAGIC_SHIELD.get()))
			event.setAmount(event.getAmount() * 0.25f);
		
		/*float damage = EntityExtension.For(event.getEntityLiving()).protect(event.getAmount());
		event.setAmount(damage);*/
	}	
	
	/*@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent e) {
		//BuffStatModifiers.instance.applyStatModifiersBasedOnBuffs(e.getEntityLiving());
		if (e.getEntityLiving().isPotionActive(ModEffects.GRAVITY_WELL.get())&& e.getEntityLiving().getMotion().y < 0) {
			e.getEntityLiving().setMotion(e.getEntityLiving().getMotion().x, e.getEntityLiving().getMotion().y * 2, e.getEntityLiving().getMotion().z);
		}
		if (e.getEntityLiving().isPotionActive(ModEffects.AGILITY.get())) {
			e.getEntityLiving().stepHeight = 1.01f;
		}else if (e.getEntityLiving().stepHeight == 1.01f) {
			e.getEntityLiving().stepHeight = 0.6f;
		}
	}*/
	
	@SubscribeEvent
	public static void playerJumpEvent(LivingJumpEvent event) {
		if (event.getEntityLiving().isPotionActive(ModEffects.AGILITY.get())){
			event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, event.getEntityLiving().getMotion().y * 1.5f, event.getEntityLiving().getMotion().z);
		}
		if (event.getEntityLiving().isPotionActive(ModEffects.ENTANGLE.get())){
			event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
		}
	}
	
	@SubscribeEvent
	public static void livingFall(LivingFallEvent e) {
		if (e.getEntityLiving().isPotionActive(ModEffects.AGILITY.get())) {
			e.setDistance(e.getDistance() / 1.5F);
		}
	}
	
	@SubscribeEvent
	public static void spellCast(SpellCastEvent.Pre e) {
		if (e.caster.isPotionActive(ModEffects.CLARITY.get())) {
			e.manaCost = 0;
			e.burnout = 0;
			EffectInstance effect = e.caster.getActivePotionEffect(ModEffects.CLARITY.get());
			e.caster.removePotionEffect(ModEffects.CLARITY.get());
			if (effect == null || effect.getAmplifier() <= 0)
				return;
			e.caster.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier() - 1));
		}
	}
	
	/*@SubscribeEvent
	public static void teleportEvent(EnderTeleportEvent e) {
		List<Long> keystoneKeys = KeystoneUtilities.instance.GetKeysInInvenory(e.getEntityLiving());
		TileEntityAstralBarrier blockingBarrier = DimensionUtilities.GetBlockingAstralBarrier(e.getEntityLiving().world, new BlockPos(e.getTargetX(), e.getTargetY(), e.getTargetZ()), keystoneKeys);

		if (e.getEntityLiving().isPotionActive(ModEffects.ASTRAL_DISTORTION.get()) || blockingBarrier != null){
			e.setCanceled(true);
			if (blockingBarrier != null){
				blockingBarrier.onEntityBlocked(e.getEntityLiving());
			}
			return;
		}
	}*/
	
	/*@SubscribeEvent
	public void playerRender(RenderPlayerEvent.Pre e) {
		if (e.getEntityLiving().isPotionActive(ModEffects.TRUE_SIGHT.get())) {
			GL11.glPushMatrix();
			GL11.glRotated(e.getPlayer().rotationYawHead, 0, -1, 0);
			int[] runes = SelectionUtils.getRuneSet(e.getPlayer());
			int numRunes = runes.length;
			double start = ((double)numRunes - 1) / 8D;
			GL11.glTranslated(-start, 2.2, 0);
			for (int rune : runes) {
				GL11.glPushMatrix();
				GL11.glScaled(0.25, 0.25, 0.25);
				Minecraft.getInstance().getItemRenderer().renderItem(e.getPlayer(), new ItemStack(ModItems.RUNE.get(), 1, rune), ItemCameraTransforms.TransformType.GUI);
				GL11.glPopMatrix();
				GL11.glTranslated(0.25, 0, 0);
			}
			GL11.glPopMatrix();
		}
	}*/
}
