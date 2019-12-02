package am2.api.skill;

import am2.api.ArsMagicaAPI;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

@SuppressWarnings("deprecation")
public class Skill extends IForgeRegistryEntry.Impl<Skill>{
	
	private int posX, posY;
	private SkillTree tree;
	private String[] parents;
	private ResourceLocation icon;
	private SkillPoint point;
	
	public Skill(ResourceLocation icon, SkillPoint point, int posX, int posY, SkillTree tree, String... string) {
		this.posX = posX;
		this.posY = posY;
		this.tree = tree;
		this.parents = string;
		this.icon = icon;
		this.point = point;
	}
	
	public Skill(String string, ResourceLocation icon, SkillPoint point, int posX, int posY, SkillTree tree, String... strings) {
		this(icon, point, posX, posY, tree, strings);
		this.setRegistryName(new ResourceLocation(ArsMagicaAPI.getCurrentModId(), string));
	}

	public String getID() {
		return getRegistryName().toString();
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public SkillTree getTree() {
		return tree;
	}
	
	public String[] getParents() {
		return parents;
	}
	
	public void writeToNBT (NBTTagCompound tag) {
		tag.setString("ID", getID());
	}
	
	public SkillPoint getPoint() {
		return point;
	}
	
	@Override
	public String toString() {
		return getID();
	}
	
	public String getName() {
		return I18n.translateToLocal("skill." + getID() + ".name");
	}
	
	public String getOcculusDesc() {
		return I18n.translateToLocal("skill." + getID() + ".occulusdesc");
	}
}
