package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.effect.EffectManaDrain;
import minecraftschurli.arsmagicalegacy.objects.effect.EffectManaRegen;
import minecraftschurli.arsmagicalegacy.objects.effect.EffectShrink;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public final class ModEffects implements IInit {
    public static final int DEFAULT_BUFF_DURATION = 10;
    public static final RegistryObject<Effect> MANA_REGEN = POTIONS.register("mana_regen", EffectManaRegen::new);
    public static final RegistryObject<Effect> MANA_DRAIN = POTIONS.register("mana_drain", EffectManaDrain::new);
    public static final RegistryObject<Effect> SHRINK = POTIONS.register("shrink", EffectShrink::new);
    public static final RegistryObject<Effect> ASTRAL_DISTORTION = null;

    public static void register() {}
}
