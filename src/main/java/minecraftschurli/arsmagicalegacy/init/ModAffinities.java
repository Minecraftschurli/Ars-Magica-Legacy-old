package minecraftschurli.arsmagicalegacy.init;

import com.google.common.collect.ImmutableSet;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.registry.AffinityRegistry;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Georg Burkl
 * @version 2020-04-22
 */
public final class ModAffinities {
    public static final RegistryObject<Affinity> NONE = AffinityRegistry.registerAffinity(Affinity.NONE, 0xFFFFFF, Affinity.NONE, ImmutableSet.of(), ImmutableSet.of());
    public static final RegistryObject<Affinity> ARCANE = AffinityRegistry.registerAffinity(Affinity.ARCANE, 0xb935cd, Affinity.NATURE, ImmutableSet.of(Affinity.LIFE, Affinity.EARTH, Affinity.WATER, Affinity.ICE), ImmutableSet.of(Affinity.AIR, Affinity.ENDER));
    public static final RegistryObject<Affinity> WATER = AffinityRegistry.registerAffinity(Affinity.WATER, 0x0b5cef, Affinity.FIRE, ImmutableSet.of(Affinity.LIGHTNING, Affinity.EARTH, Affinity.ARCANE, Affinity.ENDER), ImmutableSet.of(Affinity.AIR, Affinity.ICE));
    public static final RegistryObject<Affinity> FIRE = AffinityRegistry.registerAffinity(Affinity.FIRE, 0xef260b, Affinity.WATER, ImmutableSet.of(Affinity.AIR, Affinity.ICE, Affinity.NATURE, Affinity.LIFE), ImmutableSet.of(Affinity.EARTH, Affinity.LIGHTNING));
    public static final RegistryObject<Affinity> EARTH = AffinityRegistry.registerAffinity(Affinity.EARTH, 0x61330b, Affinity.AIR, ImmutableSet.of(Affinity.WATER, Affinity.ARCANE, Affinity.LIFE, Affinity.LIGHTNING), ImmutableSet.of(Affinity.NATURE, Affinity.FIRE));
    public static final RegistryObject<Affinity> AIR = AffinityRegistry.registerAffinity(Affinity.AIR, 0x777777, Affinity.EARTH, ImmutableSet.of(Affinity.NATURE, Affinity.FIRE, Affinity.ICE, Affinity.ENDER), ImmutableSet.of(Affinity.WATER, Affinity.ARCANE));
    public static final RegistryObject<Affinity> LIGHTNING = AffinityRegistry.registerAffinity(Affinity.LIGHTNING, 0xdece19, Affinity.ICE, ImmutableSet.of(Affinity.WATER, Affinity.ENDER, Affinity.NATURE, Affinity.EARTH), ImmutableSet.of(Affinity.LIFE, Affinity.FIRE));
    public static final RegistryObject<Affinity> ICE = AffinityRegistry.registerAffinity(Affinity.ICE, 0xd3e8fc, Affinity.LIGHTNING, ImmutableSet.of(Affinity.LIFE, Affinity.FIRE, Affinity.AIR, Affinity.ARCANE), ImmutableSet.of(Affinity.WATER, Affinity.ENDER));
    public static final RegistryObject<Affinity> NATURE = AffinityRegistry.registerAffinity(Affinity.NATURE, 0x228718, Affinity.ARCANE, ImmutableSet.of(Affinity.AIR, Affinity.ENDER, Affinity.LIGHTNING, Affinity.FIRE), ImmutableSet.of(Affinity.LIFE, Affinity.EARTH));
    public static final RegistryObject<Affinity> LIFE = AffinityRegistry.registerAffinity(Affinity.LIFE, 0x34e122, Affinity.ENDER, ImmutableSet.of(Affinity.ARCANE, Affinity.ICE, Affinity.FIRE, Affinity.EARTH), ImmutableSet.of(Affinity.NATURE, Affinity.LIGHTNING));
    public static final RegistryObject<Affinity> ENDER = AffinityRegistry.registerAffinity(Affinity.ENDER, 0x3f043d, Affinity.LIFE, ImmutableSet.of(Affinity.NATURE, Affinity.LIGHTNING, Affinity.WATER, Affinity.AIR), ImmutableSet.of(Affinity.ARCANE, Affinity.ICE));

    public static void register() {}
}
