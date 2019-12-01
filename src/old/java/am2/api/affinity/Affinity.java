package am2.api.affinity;

import java.util.ArrayList;
import java.util.Comparator;

import am2.api.ArsMagicaAPI;
import am2.utils.NBTUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * Affinity :<BR>
 * When creating an affinity you are going to need a few textures :<BR>
 * -An essence texture, located at (domain):essence_(name)<BR>
 * -An affinity tome texture, located at (domain):affinity_tome_(name)<BR>
 * -A rune texture, located at (domain):runes/rune_(name)<BR>
 * 
 * @author EdwinMindcraft
 *
 */
@SuppressWarnings("deprecation")
public class Affinity extends IForgeRegistryEntry.Impl<Affinity> implements Comparable<Affinity>{
	
	private static final ResourceLocation NONE_LOC = new ResourceLocation("arsmagica2", "none");
	private static final ResourceLocation ARCANE_LOC = new ResourceLocation("arsmagica2", "arcane");
	private static final ResourceLocation WATER_LOC = new ResourceLocation("arsmagica2", "water");
	private static final ResourceLocation FIRE_LOC = new ResourceLocation("arsmagica2", "fire");
	private static final ResourceLocation EARTH_LOC = new ResourceLocation("arsmagica2", "earth");
	private static final ResourceLocation AIR_LOC = new ResourceLocation("arsmagica2", "air");
	private static final ResourceLocation LIGHTNING_LOC = new ResourceLocation("arsmagica2", "lightning");
	private static final ResourceLocation ICE_LOC = new ResourceLocation("arsmagica2", "ice");
	private static final ResourceLocation NATURE_LOC = new ResourceLocation("arsmagica2", "nature");
	private static final ResourceLocation LIFE_LOC = new ResourceLocation("arsmagica2", "life");
	private static final ResourceLocation ENDER_LOC = new ResourceLocation("arsmagica2", "ender");
	
	public static final Affinity NONE = new Affinity("none", 0xFFFFFF).setDirectOpposite(NONE_LOC);
	public static final Affinity ARCANE = new Affinity("arcane", 0xb935cd)
			.setDirectOpposite(NATURE_LOC)
			.addMajorOpposite(LIFE_LOC, EARTH_LOC, WATER_LOC, ICE_LOC)
			.addMinorOpposite(AIR_LOC, ENDER_LOC);
	public static final Affinity WATER = new Affinity("water", 0x0b5cef)
			.setDirectOpposite(FIRE_LOC)
			.addMajorOpposite(LIGHTNING_LOC, EARTH_LOC, ARCANE_LOC, ENDER_LOC)
			.addMinorOpposite(AIR_LOC, ICE_LOC);
	public static final Affinity FIRE = new Affinity("fire", 0xef260b)
			.setDirectOpposite(WATER_LOC)
			.addMajorOpposite(AIR_LOC, ICE_LOC, NATURE_LOC, LIFE_LOC)
			.addMinorOpposite(EARTH_LOC, LIGHTNING_LOC);
	public static final Affinity EARTH = new Affinity("earth", 0x61330b)
			.setDirectOpposite(AIR_LOC)
			.addMajorOpposite(WATER_LOC, ARCANE_LOC, LIFE_LOC, LIGHTNING_LOC)
			.addMinorOpposite(NATURE_LOC, FIRE_LOC);
	public static final Affinity AIR = new Affinity("air", 0x777777)
			.setDirectOpposite(EARTH_LOC)
			.addMajorOpposite(NATURE_LOC, FIRE_LOC, ICE_LOC, ENDER_LOC)
			.addMinorOpposite(WATER_LOC, ARCANE_LOC);
	public static final Affinity LIGHTNING = new Affinity("lightning", 0xdece19)
			.setDirectOpposite(ICE_LOC)
			.addMajorOpposite(WATER_LOC, ENDER_LOC, NATURE_LOC, EARTH_LOC)
			.addMinorOpposite(LIFE_LOC, FIRE_LOC);
	public static final Affinity ICE = new Affinity("ice", 0xd3e8fc)
			.setDirectOpposite(LIGHTNING_LOC)
			.addMajorOpposite(LIFE_LOC, FIRE_LOC, AIR_LOC, ARCANE_LOC)
			.addMinorOpposite(WATER_LOC, ENDER_LOC);
	public static final Affinity NATURE = new Affinity("nature", 0x228718)
			.setDirectOpposite(ARCANE_LOC)
			.addMajorOpposite(AIR_LOC, ENDER_LOC, LIGHTNING_LOC, FIRE_LOC)
			.addMinorOpposite(LIFE_LOC, EARTH_LOC);
	public static final Affinity LIFE = new Affinity("life", 0x34e122)
			.setDirectOpposite(ENDER_LOC)
			.addMajorOpposite(ARCANE_LOC, ICE_LOC, FIRE_LOC, EARTH_LOC)
			.addMinorOpposite(NATURE_LOC, LIGHTNING_LOC);
	public static final Affinity ENDER = new Affinity("ender", 0x3f043d)
			.setDirectOpposite(LIFE_LOC)
			.addMajorOpposite(NATURE_LOC, LIGHTNING_LOC, WATER_LOC, AIR_LOC)
			.addMinorOpposite(ARCANE_LOC, ICE_LOC);
	
	public static void registerAffinities() {
		GameRegistry.register(NONE, NONE_LOC);
		GameRegistry.register(ARCANE, ARCANE_LOC);
		GameRegistry.register(WATER, WATER_LOC);
		GameRegistry.register(FIRE, FIRE_LOC);
		GameRegistry.register(EARTH, EARTH_LOC);
		GameRegistry.register(AIR, AIR_LOC);
		GameRegistry.register(LIGHTNING, LIGHTNING_LOC);
		GameRegistry.register(ICE, ICE_LOC);
		GameRegistry.register(NATURE, NATURE_LOC);
		GameRegistry.register(LIFE, LIFE_LOC);
		GameRegistry.register(ENDER, ENDER_LOC);
	}
	private int color;
	private String name;
	private ResourceLocation directOpposite;
	private ArrayList<ResourceLocation> majorOpposites = new ArrayList<>();
	private ArrayList<ResourceLocation> minorOpposites = new ArrayList<>();
	
	public Affinity (String name, int color) {
		this.color = color;
		this.name = name;
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
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getRegistryName().toString();
	}
	
	/**
	 * 
	 * @return the localized name of the affinity
	 */
	public String getLocalizedName() {
		return I18n.translateToLocal(getUnlocalisedName());
	}
	/**
	 * 
	 * @return the unlocalized name of the affinity (affinity.name)
	 */
	public String getUnlocalisedName() {
		return "affinity." + name;
	}
	/**
	 * Will write to an existing {@link NBTTagCompound} the data of the affinity
	 * 
	 * @param tag       Root tag of the entity / item
	 * @param affinity  The affinity to add
	 * @param depth     The the depth of this affinity
	 */
	public static void writeToNBT (NBTTagCompound tag, Affinity affinity, double depth) {
		NBTTagList affinityTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(tag), "Affinity");
		NBTTagCompound tmp = new NBTTagCompound();
		tmp.setString("Name", affinity.getRegistryName().toString());
		tmp.setDouble("Depth", depth);
		affinityTag.appendTag(tmp);
		NBTUtils.getAM2Tag(tag).setTag("Affinity", affinityTag);
	}
	
	/**
	 * Will list all the affinities the player / item has if given a correct {@link NBTTagCompound}
	 * 
	 * @param tag       Root tag of the entity / item
	 * @return          A list of all the affinities this player / item has
	 */
	public static ArrayList<Affinity> readFromNBT (NBTTagCompound tag) {
		NBTTagList affinityTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(tag), "Affinity");
		ArrayList<Affinity> affinities = new ArrayList<Affinity>();
		for (int i = 0; i < affinityTag.tagCount(); i++) {
			NBTTagCompound tmp = affinityTag.getCompoundTagAt(i);
			Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObject(new ResourceLocation(tmp.getString("Name")));
			affinities.add(aff);
		}
		return affinities;
	}
	/**
	 * Will give the depth of an affinity the player / item has if given a correct {@link NBTTagCompound}
	 * 
	 * @param tag       Root tag of the entity / item
	 * @return          Depth in the affinity
	 */
	public double readDepth (NBTTagCompound tag) {
		NBTTagList affinityTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(tag), "Affinity");
		for (int i = 0; i < affinityTag.tagCount(); i++) {
			NBTTagCompound tmp = affinityTag.getCompoundTagAt(i);
			if (tmp.getString("Name").equals(this.getRegistryName().toString()))
				return tmp.getDouble("Depth");
		}
		return 0.0F;
	}

	public int compareTo(Affinity b) {
		return ArsMagicaAPI.getAffinityRegistry().getId(b) - ArsMagicaAPI.getAffinityRegistry().getId(this);
	}

	public ArrayList<Affinity> getMinorOpposingAffinities() {
		ArrayList<Affinity> returnList = new ArrayList<>();
		for (ResourceLocation rl : minorOpposites) {
			Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObject(rl);
			if (aff != NONE)
				returnList.add(aff);
		}
		return returnList;
	}
	
	public ArrayList<Affinity> getMajorOpposingAffinities() {
		ArrayList<Affinity> returnList = new ArrayList<>();
		for (ResourceLocation rl : majorOpposites) {
			Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObject(rl);
			if (aff != NONE)
				returnList.add(aff);
		}
		return returnList;
	}
	
	public ArrayList<Affinity> getAdjacentAffinities() {
		ArrayList<Affinity> returnList = new ArrayList<>();
		for (ResourceLocation rl : ArsMagicaAPI.getAffinityRegistry().getKeys()) {
			Affinity aff = ArsMagicaAPI.getAffinityRegistry().getObject(rl);
			if (aff == NONE || majorOpposites.contains(rl) || minorOpposites.contains(rl) || directOpposite == rl || aff == this)
				continue;
			returnList.add(aff);
		}
		return returnList;
	}
	
	public Affinity getOpposingAffinity() {
		return ArsMagicaAPI.getAffinityRegistry().getObject(directOpposite);
	}
	
	public Affinity setDirectOpposite(ResourceLocation directOpposite) {
		this.directOpposite = directOpposite;
		return this;
	}
	
	public Affinity addMajorOpposite(ResourceLocation... rls) {
		for (ResourceLocation rl : rls)
			majorOpposites.add(rl);
		return this;
	}
	
	public Affinity addMinorOpposite(ResourceLocation... rls) {
		for (ResourceLocation rl : rls)
			minorOpposites.add(rl);
		return this;
	}
	
	public static class AffinityComparator implements Comparator<Affinity> {

		@Override
		public int compare(Affinity o1, Affinity o2) {
			return o1.compareTo(o2);
		}
		
	}
}
