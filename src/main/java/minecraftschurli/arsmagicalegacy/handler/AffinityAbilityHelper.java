package minecraftschurli.arsmagicalegacy.handler;

import java.util.HashSet;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AffinityAbilityHelper {
	
	/*static {
		//AIR
		GameRegistry.register(new AbilityLightAsAFeather());
		GameRegistry.register(new AbilityAgile());
		
		//ARCANE
		GameRegistry.register(new AbilityClearCaster());
		GameRegistry.register(new AbilityMagicWeakness());
		GameRegistry.register(new AbilityOneWithMagic());
		
		//EARTH
		GameRegistry.register(new AbilitySolidBones());
		
		//ENDER
		GameRegistry.register(new AbilityRelocation());
		GameRegistry.register(new AbilityNightVision());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.ENDER));
		GameRegistry.register(new AbilityPoisonResistance());
		GameRegistry.register(new AbilitySunlightWeakness());
		
		//FIRE
		GameRegistry.register(new AbilityFireResistance());
		GameRegistry.register(new AbilityFirePunch());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.FIRE));
		
		//ICE
		GameRegistry.register(new AbilityLavaFreeze());
		GameRegistry.register(new AbilityWaterFreeze());
		GameRegistry.register(new AbilityColdBlooded());
		
		//LIFE
		GameRegistry.register(new AbilityFastHealing());
		GameRegistry.register(new AbilityPacifist());
		
		//WATER
		GameRegistry.register(new AbilityExpandedLungs());
		GameRegistry.register(new AbilityFluidity());
		GameRegistry.register(new AbilitySwiftSwim());
		GameRegistry.register(new AbilityFireWeakness());
		GameRegistry.register(new AbilityAntiEndermen());
		
		//NATURE
		GameRegistry.register(new AbilityRooted());
		GameRegistry.register(new AbilityThorns());
		GameRegistry.register(new AbilityLeafLike());
		GameRegistry.register(new AbilityPhotosynthesis());
		
		//LIGHTNING
		GameRegistry.register(new AbilityLightningStep());
		GameRegistry.register(new AbilityReflexes());
		GameRegistry.register(new AbilityFulmination());
		GameRegistry.register(new AbilityShortCircuit());
		GameRegistry.register(new AbilityThunderPunch());
		GameRegistry.register(new AbilityWaterWeakness(Affinity.LIGHTNING));
	}*/

    @SuppressWarnings("UnstableApiUsage")
    private static final Multimap<AbstractAffinityAbility.AbilityListenerType, AbstractAffinityAbility> listeners = MultimapBuilder.enumKeys(AbstractAffinityAbility.AbilityListenerType.class).hashSetValues().build();

    public static void registerListeners() {
        for (AbstractAffinityAbility ability : RegistryHandler.getAffinityAbilityRegistry().getValues()) {
            for (AbstractAffinityAbility.AbilityListenerType type : ability.registerListeners(new HashSet<>())) {
                listeners.put(type, ability);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.KEY_PRESS)) {
            if (ability.getKey() != null && event.getKey() == ability.getKey().getKey().getKeyCode()) {
                //if (FMLCommonHandler.instance().getMinecraftServerInstance() == null)
                //	return;
                PlayerEntity p = ArsMagicaAPI.getLocalPlayer();
                PlayerEntity player = p.getEntityWorld().getPlayerByUuid(p.getUniqueID());
                if (ability.canApply(player)) {
                    ability.createRunnable(player).run();
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            if (!event.getEntityLiving().world.isRemote) {
                for (Entry<String, Integer> entry : CapabilityHelper.getAbilityCooldowns(event.getEntityLiving()).entrySet()) {
                    if (entry.getValue() > 0)
                        CapabilityHelper.addAbilityCooldown(event.getEntityLiving(), entry.getKey(), entry.getValue() - 1);
                }
            }
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.TICK)) {
                if (ability.canApply((PlayerEntity) event.getEntityLiving()))
                    ability.applyTick((PlayerEntity) event.getEntityLiving());
                else
                    ability.removeEffects((PlayerEntity) event.getEntityLiving());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.HURT)) {
                if (ability.canApply((PlayerEntity) event.getEntityLiving()))
                    ability.applyHurt((PlayerEntity) event.getEntityLiving(), event);
            }
        }
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.HURTING)) {
                if (ability.canApply((PlayerEntity) event.getSource().getTrueSource()))
                    ability.applyHurting((PlayerEntity) event.getSource().getTrueSource(), event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.FALL)) {
                if (ability.canApply((PlayerEntity) event.getEntityLiving()))
                    ability.applyFall((PlayerEntity) event.getEntityLiving(), event);
            }
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.DEATH)) {
                if (ability.canApply((PlayerEntity) event.getEntityLiving()))
                    ability.applyDeath((PlayerEntity) event.getEntityLiving(), event);
            }
        }
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.KILL)) {
                if (ability.canApply((PlayerEntity) event.getSource().getTrueSource()))
                    ability.applyKill((PlayerEntity) event.getSource().getTrueSource(), event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.JUMP)) {
                if (ability.canApply((PlayerEntity) event.getEntityLiving()))
                    ability.applyJump((PlayerEntity) event.getEntityLiving(), event);
            }
        }
    }

    @SubscribeEvent
    public void onSpellCast(SpellCastEvent.Post event) {
        if (event.caster instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.SPELL_CAST)) {
                if (ability.canApply((PlayerEntity) event.caster))
                    ability.applySpellCast((PlayerEntity) event.caster, event);
            }
        }
    }

    @SubscribeEvent
    public void onPreSpellCast(SpellCastEvent.Pre event) {
        if (event.caster instanceof PlayerEntity) {
            for (AbstractAffinityAbility ability : listeners.get(AbstractAffinityAbility.AbilityListenerType.PRE_SPELL_CAST)) {
                if (ability.canApply((PlayerEntity) event.caster))
                    ability.applyPreSpellCast((PlayerEntity) event.caster, event);
            }
        }
    }
}
