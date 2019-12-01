package am2.gui.controls;

import am2.api.skill.SkillTree;
import am2.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSkillTree extends GuiButton{
	
	SkillTree tree;
	boolean inverted;
	public final int page;
	
	public GuiButtonSkillTree(int buttonId, int x, int y, SkillTree tree, int page, boolean inverted) {
		super(buttonId, x, y, 22, 22, "");
		this.tree = tree;
		this.inverted = inverted;
		this.page = page;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("arsmagica2", "textures/occulus/overlay.png"));
			float f = 0.00390625F;
			if (inverted)
				RenderUtils.drawBox(xPosition, yPosition, 22, 22, zLevel, 22*f, 210*f + 22*f, 0, 210*f);			
			else
				RenderUtils.drawBox(xPosition, yPosition, 22, 22, zLevel, 0, 210*f, 22*f, 210*f + 22*f);
			//drawTexturedModalRect(xPosition, yPosition, 0, 210, 22, 22);
			mc.renderEngine.bindTexture(tree.getIcon());
			RenderUtils.drawBox(xPosition + 2F, yPosition + 2F, 18, 18, zLevel, 0, 0, 1, 1);
		}
	}
	
	public SkillTree getTree() {
		return tree;
	}
}
