package am2.particles;

import am2.api.affinity.Affinity;

public class AMParticleDefs {

    public static String getParticleForAffinity(Affinity aff){
        if (aff.equals(Affinity.AIR)) return "wind";
        if (aff.equals(Affinity.ARCANE)) return "arcane";
        if (aff.equals(Affinity.EARTH)) return "rock";
        if (aff.equals(Affinity.ENDER)) return "pulse";
        if (aff.equals(Affinity.FIRE)) return "explosion_2";
        if (aff.equals(Affinity.ICE)) return "ember";
        if (aff.equals(Affinity.LIFE)) return "sparkle";
        if (aff.equals(Affinity.LIGHTNING)) return "lightning_hand";
        if (aff.equals(Affinity.NATURE)) return "plant";
        if (aff.equals(Affinity.WATER)) return "water_ball";
        if (aff.equals(Affinity.NONE)) return "lens_flare";
        return "lens_flare";
    }

    public static String getSecondaryParticleForAffinity(Affinity aff){
        if (aff.equals(Affinity.AIR)) return "air_hand";
        if (aff.equals(Affinity.ARCANE)) return "symbols";
        if (aff.equals(Affinity.EARTH)) return "earth_hand";
        if (aff.equals(Affinity.ENDER)) return "ghost";
        if (aff.equals(Affinity.FIRE)) return "smoke";
        if (aff.equals(Affinity.ICE)) return "snowflakes";
        if (aff.equals(Affinity.LIFE)) return "sparkle2";
        if (aff.equals(Affinity.LIGHTNING)) return "lightning_hand";
        if (aff.equals(Affinity.NATURE)) return "leaf";
        if (aff.equals(Affinity.WATER)) return "water_hand";
        if (aff.equals(Affinity.NONE)) return "lights";
        return "lights";

    }
}
