package minecraftschurli.arsmagicalegacy.api.registry;

import minecraftschurli.arsmagicalegacy.api.affinity.AbstractAffinityAbility;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class AbilityRegistry {
	private static final Map<RegistryObject<AbstractAffinityAbility>, Supplier<? extends AbstractAffinityAbility>> abilities = new LinkedHashMap<>();

	static void onAbilityRegister(RegistryEvent.Register<AbstractAffinityAbility> event) {
		if (event.getGenericType() != AbstractAffinityAbility.class)
			return;
		IForgeRegistry<AbstractAffinityAbility> reg = event.getRegistry();
		for (Map.Entry<RegistryObject<AbstractAffinityAbility>, Supplier<? extends AbstractAffinityAbility>> e : abilities.entrySet()) {
			reg.register(e.getValue().get());
			e.getKey().updateReference(reg);
		}
	}
}
