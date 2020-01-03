package am2.api.compendium;

import am2.api.compendium.pages.*;
import com.google.common.collect.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class CompendiumEntry {
	
	private CompendiumCategory category;
	private String id;
	private Object renderObject;
	private ArrayList<Object> objects;
	private boolean isDefaultUnlocked;
	
	public CompendiumEntry(@Nullable Object renderObject, String id) {
		if (id.contains("\\.")) throw new IllegalArgumentException("Entry ids can't contain \"\\.\"");
		this.id = id;
		this.objects = new ArrayList<>();
		this.renderObject = renderObject;
	}
	
	protected CompendiumEntry setCategory (CompendiumCategory category) {
		this.category = category;
		return this;
	}
	
	public boolean canBeDisplayed(String categoryID) {
		return categoryID.equals(category.getID());
	}
	
	public String getID() {
		return category.getID() + "." + id;
	}
	
	public String getName() {
		return I18n.translateToLocal("compendium." + this.getID() + ".name");
	}
	
	public String getDescription() {
		return I18n.translateToLocal("compendium." + this.getID() + ".desc");
	}
	
	public boolean isDefaultUnlocked() {
		return isDefaultUnlocked;
	}
	
	public Object getRenderObject() {
		return renderObject;
	}
	
	public CompendiumEntry setUnlocked() {
		this.isDefaultUnlocked = true;
		return this;
	}
	
	public CompendiumEntry addObject(Object obj) {
		objects.add(obj);
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public ArrayList<CompendiumPage<?>> getPages() {
		ArrayList<CompendiumPage<?>> pages = new ArrayList<>();
		for (Object obj : objects) {
			if (obj == null) continue;
			CompendiumPage<?> page = CompendiumPage.getCompendiumPage(obj.getClass(), obj);
			if (page != null)
				pages.add(page);
		}
		return pages;
	}
	
	public ImmutableList<Object> getObjects() {
		return ImmutableList.copyOf(objects);
	}
}
