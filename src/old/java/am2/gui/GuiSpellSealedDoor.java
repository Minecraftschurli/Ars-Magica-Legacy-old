package am2.gui;

import am2.blocks.tileentity.*;
import am2.container.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class GuiSpellSealedDoor extends GuiContainer{

	GuiButton radiusButton;

	private static final ResourceLocation background = new ResourceLocation("arsmagica2", "textures/gui/astralBarrierGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}

	public GuiSpellSealedDoor(InventoryPlayer inventoryplayer, TileEntitySpellSealedDoor door){
		super(new ContainerSpellSealedDoor(inventoryplayer, door));
		xSize = 176;
		ySize = 180;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
	}
}
