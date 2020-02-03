package am2.gui;

import am2.blocks.tileentity.*;
import am2.container.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class GuiArcaneDeconstructor extends GuiContainer{

	private TileEntityArcaneDeconstructor deconstructor;

	private static final ResourceLocation background = new ResourceLocation("arsmagica2", "textures/gui/deconstruction_table_GUI.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		mc.renderEngine.bindTexture(background);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		int overlayHeight = this.deconstructor.getProgressScaled(18);
		if (overlayHeight > 0)
			this.drawTexturedModalRect(l + 79, i1 + 65, 176, 0, 17, overlayHeight);
	}

	public GuiArcaneDeconstructor(InventoryPlayer inventoryplayer, TileEntityArcaneDeconstructor deconstructorEntity){
		super(new ContainerArcaneDeconstructor(inventoryplayer, deconstructorEntity));
		this.deconstructor = deconstructorEntity;
		xSize = 176;
		ySize = 198;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
	}
}
