package minecraftschurli.arsmagicalegacy.api.affinity;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2020-02-13
 */
public class Affinity extends ForgeRegistryEntry<Affinity> implements Comparable<Affinity> {
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

    private int color;
    private ResourceLocation directOpposite;
    private Set<ResourceLocation> majorOpposites = new HashSet<>();
    private Set<ResourceLocation> minorOpposites = new HashSet<>();
    private IItemProvider essence = Items.AIR;

    public Affinity(int color) {
        this.color = color;
    }

    public String getTranslationKey() {
        return Util.makeTranslationKey("affinity", ArsMagicaAPI.getAffinityRegistry().getKey(this));
    }

    public ITextComponent getName() {
        return new TranslationTextComponent(this.getTranslationKey());
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
        return minorOpposites
                .stream()
                .filter(location -> !location.equals(NONE))
                .map(ArsMagicaAPI.getAffinityRegistry()::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Affinity> getMajorOpposingAffinities() {
        return majorOpposites
                .stream()
                .filter(location -> !location.equals(NONE))
                .map(ArsMagicaAPI.getAffinityRegistry()::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Affinity> getAdjacentAffinities() {
        return ArsMagicaAPI.getAffinityRegistry()
                .getKeys()
                .stream()
                .filter(rl -> !(Objects.equals(rl, NONE) || majorOpposites.contains(rl) || minorOpposites.contains(rl) || Objects.equals(directOpposite, rl) || Objects.equals(getRegistryName(), rl)))
                .map(ArsMagicaAPI.getAffinityRegistry()::getValue)
                .collect(Collectors.toSet());
    }

    public Affinity getOpposingAffinity() {
        return ArsMagicaAPI.getAffinityRegistry().getValue(directOpposite);
    }

    public Affinity setDirectOpposite(ResourceLocation directOpposite) {
        this.directOpposite = directOpposite;
        return this;
    }

    public Affinity addMajorOpposite(Collection<ResourceLocation> rls) {
        this.majorOpposites.addAll(rls);
        return this;
    }

    public Affinity addMinorOpposite(Collection<ResourceLocation> rls) {
        this.minorOpposites.addAll(rls);
        return this;
    }

    public Affinity addMajorOpposite(ResourceLocation... rls) {
        return addMajorOpposite(Arrays.asList(rls));
    }

    public Affinity addMinorOpposite(ResourceLocation... rls) {
        return addMinorOpposite(Arrays.asList(rls));
    }

    public Affinity addMajorOpposite(ResourceLocation rl) {
        this.majorOpposites.add(rl);
        return this;
    }

    public Affinity addMinorOpposite(ResourceLocation rl) {
        this.minorOpposites.add(rl);
        return this;
    }

    public void setEssence(IItemProvider essence) {
        this.essence = essence;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@Nonnull Affinity o) {
        if (o.getRegistryName() == null && this.getRegistryName() != null)
            return 1;
        return Objects.compare(ArsMagicaAPI.getAffinityRegistry().getKey(this), ArsMagicaAPI.getAffinityRegistry().getKey(o), Comparator.comparing(ResourceLocation::toString));
    }

    public IItemProvider getEssence() {
        return this.essence;
    }
}
