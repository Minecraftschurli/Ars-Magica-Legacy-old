package minecraftschurli.arsmagicalegacy.api.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class AbilityRegistry {
	private static final Map<RegistryObject<AbstractAffinityAbility>, Supplier<? extends AbstractAffinityAbility>> abilities = new LinkedHashMap<>();

	public static RegistryObject<AbstractAffinityAbility> registerAbility(String modid, String key, Supplier<AbstractAffinityAbility> ability) {
		return registerAbility(new ResourceLocation(modid, key), ability);
	}

	public static RegistryObject<AbstractAffinityAbility> registerAbility(ResourceLocation key, Supplier<AbstractAffinityAbility> ability) {
		Objects.requireNonNull(key);
		RegistryObject<AbstractAffinityAbility> ret = RegistryObject.of(key, RegistryHandler.getAffinityAbilityRegistry());
		if (abilities.putIfAbsent(ret, () -> ability.get().setRegistryName(key)) != null) throw new IllegalArgumentException("Duplicate registration " + key.toString());
		return ret;
	}

	static void onAbilityRegister(RegistryEvent.Register<AbstractAffinityAbility> event) {
		if (event.getGenericType() != AbstractAffinityAbility.class) return;
		IForgeRegistry<AbstractAffinityAbility> reg = event.getRegistry();
		for (Map.Entry<RegistryObject<AbstractAffinityAbility>, Supplier<? extends AbstractAffinityAbility>> e : abilities.entrySet()) {
			reg.register(e.getValue().get());
			e.getKey().updateReference(reg);
		}
	}
}
