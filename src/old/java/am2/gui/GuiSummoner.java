package am2.gui;

import am2.blocks.tileentity.*;
import am2.container.*;
import am2.power.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;
import org.lwjgl.opengl.*;

@SuppressWarnings("deprecation")
public class GuiSummoner extends GuiContainer{

	private static final ResourceLocation background = new ResourceLocation("arsmagica2", "textures/gui/SummonerGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
		mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}

	public GuiSummoner(InventoryPlayer inventoryplayer, TileEntitySummoner summoner){
		super(new ContainerSummoner(inventoryplayer, summoner));
		summonerInventory = summoner;
		xSize = 176;
		ySize = 245;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){

		String essenceString = I18n.translateToLocal("am2.gui.summonCost") + ":";
		String maintainString = I18n.translateToLocal("am2.gui.maintainCost") + ":";
		float cost = summonerInventory.getSummonCost();
		float maintainCost = summonerInventory.getMaintainCost() * 20;
		String essenceCostString = cost >= 0 ? String.format("%.2f/s", maintainCost) : "N/A";
		int color = cost >= 0 ? cost <= PowerNodeRegistry.For(Minecraft.getMinecraft().theWorld).getHighestPower(summonerInventory) ? 0x007700 : 0x770000 : 0x333333;

		int offset = fontRendererObj.getStringWidth(essenceString) + 25;


		fontRendererObj.drawString(maintainString, xSize - offset, ySize - 130, 0x777777);
		fontRendererObj.drawString(essenceCostString, xSize - offset, ySize - 120, color);

		essenceCostString = cost >= 0 ? String.format("%.2f", cost) : "N/A";
		color = cost >= 0 ? cost <= PowerNodeRegistry.For(Minecraft.getMinecraft().theWorld).getHighestPower(summonerInventory) ? 0x007700 : 0x770000 : 0x333333;

		fontRendererObj.drawString(essenceString, 20, ySize - 130, 0x777777);
		fontRendererObj.drawString(essenceCostString, 20, ySize - 120, color);

		String readyString = summonerInventory.canSummon() ? I18n.translateToLocal("am2.gui.summonReady") : I18n.translateToLocal("am2.gui.summonNotReady");
		color = summonerInventory.canSummon() ? 0x007700 : 0x770000;

		fontRendererObj.drawString(readyString, xSize / 2 - (fontRendererObj.getStringWidth(readyString) / 2), ySize - 107, color);
	}

	private final TileEntitySummoner summonerInventory;

}
