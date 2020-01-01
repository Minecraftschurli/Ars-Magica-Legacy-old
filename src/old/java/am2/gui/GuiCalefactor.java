package am2.gui;

import am2.blocks.tileentity.*;
import am2.container.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class GuiCalefactor extends GuiContainer{

	private static final ResourceLocation background = new ResourceLocation("arsmagica2", "textures/gui/calefactorGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		int overlayHeight = this.calefactorInventory.getCookProgressScaled(18);
		if (overlayHeight > 0)
			this.drawTexturedModalRect(l + 79, i1 + 65, 176, 0, 17, overlayHeight);
	}

	public GuiCalefactor(EntityPlayer player, TileEntityCalefactor tileEntityCalefactor){
		super(new ContainerCalefactor(player, tileEntityCalefactor));
		calefactorInventory = tileEntityCalefactor;
		xSize = 176;
		ySize = 204;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
	}

	private final TileEntityCalefactor calefactorInventory;
}
