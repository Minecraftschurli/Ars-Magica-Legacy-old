package minecraftschurli.arsmagicalegacy.api.affinity;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class Affinity extends ForgeRegistryEntry<Affinity> implements Comparable<Affinity>, ITranslatable<Affinity> {
    public static final ResourceLocation NONE = new ResourceLocation(ArsMagicaAPI.MODID, "none");
    public static final ResourceLocation ARCANE = new ResourceLocation(ArsMagicaAPI.MODID, "arcane");
    public static final ResourceLocation WATER = new ResourceLocation(ArsMagicaAPI.MODID, "water");
    public static final ResourceLocation FIRE = new ResourceLocation(ArsMagicaAPI.MODID, "fire");
    public static final ResourceLocation EARTH = new ResourceLocation(ArsMagicaAPI.MODID, "earth");
    public static final ResourceLocation AIR = new ResourceLocation(ArsMagicaAPI.MODID, "air");
    public static final ResourceLocation LIGHTNING = new ResourceLocation(ArsMagicaAPI.MODID, "lightning");
    public static final ResourceLocation ICE = new ResourceLocation(ArsMagicaAPI.MODID, "ice");
    public static final ResourceLocation NATURE = new ResourceLocation(ArsMagicaAPI.MODID, "nature");
    public static final ResourceLocation LIFE = new ResourceLocation(ArsMagicaAPI.MODID, "life");
    public static final ResourceLocation ENDER = new ResourceLocation(ArsMagicaAPI.MODID, "ender");
    private final int color;
    private ResourceLocation directOpposite;
    private final Set<ResourceLocation> majorOpposites = new HashSet<>();
    private final Set<ResourceLocation> minorOpposites = new HashSet<>();
    private IItemProvider essence = Items.AIR;

    public Affinity(int color) {
        this.color = color;
    }

    @Override
    public String getType() {
        return "affinity";
    }

    /**
     * Rendering purpose
     * Will return the color used by the occulus to render this affinity depth
     *
     * @return the color of the affinity
     */
    public int getColor() {
        return color;
    }

    public Set<Affinity> getMinorOpposingAffinities() {
        return minorOpposites.stream().filter(location -> !location.equals(NONE)).map(RegistryHandler.getAffinityRegistry()::getValue).collect(Collectors.toSet());
    }

    public Set<Affinity> getMajorOpposingAffinities() {
        return majorOpposites.stream().filter(location -> !location.equals(NONE)).map(RegistryHandler.getAffinityRegistry()::getValue).collect(Collectors.toSet());
    }

    public Set<Affinity> getAdjacentAffinities() {
        return RegistryHandler.getAffinityRegistry().getKeys().stream().filter(rl -> !(Objects.equals(rl, NONE) || majorOpposites.contains(rl) || minorOpposites.contains(rl) || Objects.equals(directOpposite, rl) || Objects.equals(getRegistryName(), rl))).map(RegistryHandler.getAffinityRegistry()::getValue).collect(Collectors.toSet());
    }

    public Affinity getOpposingAffinity() {
        return RegistryHandler.getAffinityRegistry().getValue(directOpposite);
    }

    public Affinity setDirectOpposite(ResourceLocation directOpposite) {
        this.directOpposite = directOpposite;
        return this;
    }

    public Affinity addMajorOpposite(Collection<ResourceLocation> rls) {
        majorOpposites.addAll(rls);
        return this;
    }

    public Affinity addMinorOpposite(Collection<ResourceLocation> rls) {
        minorOpposites.addAll(rls);
        return this;
    }

    public Affinity addMajorOpposite(ResourceLocation... rls) {
        return addMajorOpposite(Arrays.asList(rls));
    }

    public Affinity addMinorOpposite(ResourceLocation... rls) {
        return addMinorOpposite(Arrays.asList(rls));
    }

    public Affinity addMajorOpposite(ResourceLocation rl) {
        majorOpposites.add(rl);
        return this;
    }

    public Affinity addMinorOpposite(ResourceLocation rl) {
        minorOpposites.add(rl);
        return this;
    }

    public void setEssence(IItemProvider essence) {
        this.essence = essence;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }

    @Override
    public int compareTo(@Nonnull Affinity o) {
        if (o.getRegistryName() == null && getRegistryName() != null) return 1;
        return Objects.compare(RegistryHandler.getAffinityRegistry().getKey(this), RegistryHandler.getAffinityRegistry().getKey(o), Comparator.comparing(ResourceLocation::toString));
    }

    public IItemProvider getEssence() {
        return essence;
    }

    @Nullable
    public ResourceLocation getTextureLocation(String suffix) {
        ResourceLocation rl = getRegistryName();
        if (rl == null || rl.equals(NONE)) return null;
        return new ResourceLocation(rl.getNamespace(), rl.getPath()+"_"+suffix);
    }
}
