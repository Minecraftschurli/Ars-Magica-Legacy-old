package am2.gui;

import am2.*;
import am2.api.math.*;
import am2.gui.controls.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.translation.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

import java.io.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class GuiHudCustomization extends GuiScreen{
	private GuiButtonVariableDims manaButton;
	private GuiButtonVariableDims burnoutButton;
	private GuiButtonVariableDims levelButton;
	private GuiButtonVariableDims affinityButton;
	private GuiButtonVariableDims positiveBuffs;
	private GuiButtonVariableDims negativeBuffs;
	private GuiButtonVariableDims armorHead;
	private GuiButtonVariableDims armorChest;
	private GuiButtonVariableDims armorLegs;
	private GuiButtonVariableDims armorBoots;
	private GuiButtonVariableDims xpBar;
	private GuiButtonVariableDims contingency;
	private GuiButtonVariableDims manaShielding;
	
	private GuiButtonVariableDims manaNumeric;
	private GuiButtonVariableDims burnoutNumeric;
	private GuiButtonVariableDims XPNumeric;

	private GuiButtonVariableDims spellBook;

	private GuiButtonVariableDims options;
	private GuiButtonVariableDims showBuffs;
	private GuiButtonVariableDims showNumerics;
	private GuiButtonVariableDims showHudMinimally;
	private GuiButtonVariableDims showArmorUI;
	private GuiButtonVariableDims showXPAlways;
	private GuiButtonVariableDims showHudBars;

	private boolean doShowBuffs;
	private boolean doShowNumerics;
	private boolean doShowHudMinimally;
	private boolean doShowArmor;
	private boolean doShowXPAlways;
	private boolean doShowBars;
	private boolean showOptions;

	private GuiButtonVariableDims dragTarget;
	private AMVector2 dragOffset;

	private final HashMap<Integer, Integer> snapData;

	int screenWidth = 1;
	int screenHeight = 1;

	public GuiHudCustomization(){
		snapData = new HashMap<Integer, Integer>();
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
		screenWidth = scaledresolution.getScaledWidth();
		screenHeight = scaledresolution.getScaledHeight();
	}

	@Override
	public void initGui(){
		super.initGui();

		doShowBuffs = ArsMagica2.config.getShowBuffs();
		doShowNumerics = ArsMagica2.config.getShowNumerics();
		doShowHudMinimally = ArsMagica2.config.showHudMinimally();
		doShowArmor = ArsMagica2.config.showArmorUI();
		doShowXPAlways = ArsMagica2.config.showXPAlways();
		doShowBars = ArsMagica2.config.showHudBars();

		int barWidth = (width / 8) + 16;

		manaButton = new GuiButtonVariableDims(0, 0, 0, "").setDimensions(barWidth, 14).setBorderOnly(true);
		burnoutButton = new GuiButtonVariableDims(1, 0, 0, "").setDimensions(barWidth, 14).setBorderOnly(true);
		levelButton = new GuiButtonVariableDims(2, 0, 0, "").setDimensions(10, 10).setBorderOnly(true);
		affinityButton = new GuiButtonVariableDims(3, 0, 0, "").setDimensions(10, 20).setBorderOnly(true).setPopupText(I18n.translateToLocal("am2.gui.affinity"));
		positiveBuffs = new GuiButtonVariableDims(4, 0, 0, "").setPopupText(I18n.translateToLocal("am2.gui.positiveBuffs")).setDimensions(10, 10).setBorderOnly(true);
		negativeBuffs = new GuiButtonVariableDims(5, 0, 0, "").setPopupText(I18n.translateToLocal("am2.gui.negativeBuffs")).setDimensions(10, 10).setBorderOnly(true);
		armorHead = new GuiButtonVariableDims(6, 0, 0, "").setDimensions(10, 10).setPopupText(I18n.translateToLocal("am2.gui.headwear")).setBorderOnly(true);
		armorChest = new GuiButtonVariableDims(7, 0, 0, "").setDimensions(10, 10).setPopupText(I18n.translateToLocal("am2.gui.chestplate")).setBorderOnly(true);
		armorLegs = new GuiButtonVariableDims(8, 0, 0, "").setDimensions(10, 10).setPopupText(I18n.translateToLocal("am2.gui.leggings")).setBorderOnly(true);
		armorBoots = new GuiButtonVariableDims(9, 0, 0, "").setDimensions(10, 10).setPopupText(I18n.translateToLocal("am2.gui.boots")).setBorderOnly(true);
		xpBar = new GuiButtonVariableDims(10, 0, 0, "").setDimensions(182, 5).setPopupText(I18n.translateToLocal("am2.gui.xpBar")).setBorderOnly(true);
		contingency = new GuiButtonVariableDims(10, 0, 0, "").setDimensions(16, 16).setPopupText(I18n.translateToLocal("am2.gui.contingency")).setBorderOnly(true);
		showBuffs = new GuiButtonVariableDims(11, width / 2 - 90, height - 88, I18n.translateToLocal("am2.gui.buffTimers")).setDimensions(180, 20);
		showNumerics = new GuiButtonVariableDims(12, width / 2 - 90, height - 66, I18n.translateToLocal("am2.gui.numericValues")).setDimensions(180, 20);
		options = new GuiButtonVariableDims(13, width / 2 - 90, height - 22, I18n.translateToLocal("am2.gui.options")).setDimensions(180, 20);
		showHudMinimally = new GuiButtonVariableDims(14, width / 2 - 90, height - 110, I18n.translateToLocal("am2.gui.minimalHud")).setDimensions(180, 20).setPopupText(I18n.translateToLocal("am2.gui.minimalHudDesc"));
		showArmorUI = new GuiButtonVariableDims(15, width / 2 - 90, height - 132, I18n.translateToLocal("am2.gui.armorUI")).setDimensions(180, 20);
		showXPAlways = new GuiButtonVariableDims(16, width / 2 - 90, height - 154, I18n.translateToLocal("am2.gui.xpAlways")).setDimensions(180, 20);
		showHudBars = new GuiButtonVariableDims(17, width / 2 - 90, height - 176, I18n.translateToLocal("am2.gui.hudBars")).setDimensions(180, 20);
		
		manaNumeric = new GuiButtonVariableDims(18, 0, 0, "").setDimensions(25, 10).setPopupText(I18n.translateToLocal("am2.gui.manaNumeric")).setBorderOnly(true);
		burnoutNumeric = new GuiButtonVariableDims(19, 0, 0, "").setDimensions(25, 10).setPopupText(I18n.translateToLocal("am2.gui.burnoutNumeric")).setBorderOnly(true);
		XPNumeric = new GuiButtonVariableDims(20, 0, 0, "").setDimensions(25, 10).setPopupText(I18n.translateToLocal("am2.gui.XPNumeric")).setBorderOnly(true);

		spellBook = new GuiButtonVariableDims(21, 0, 0, I18n.translateToLocal("item.arsmagica2:spellBook.name")).setBorderOnly(true).setDimensions(106, 15);
		
		manaShielding = new GuiButtonVariableDims(22, 0, 0, "").setDimensions(90, 9).setBorderOnly(true).setPopupText(I18n.translateToLocal("am2.gui.manaShielding"));

		showBuffs.displayString = I18n.translateToLocal("am2.gui.buffTimers") + ": " + ((doShowBuffs) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
		showNumerics.displayString = I18n.translateToLocal("am2.gui.numericValues") + ": " + ((doShowNumerics) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
		showHudMinimally.displayString = I18n.translateToLocal("am2.gui.minimalHud") + ": " + ((doShowHudMinimally) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
		showArmorUI.displayString = I18n.translateToLocal("am2.gui.armorUI") + ": " + ((doShowArmor) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
		showXPAlways.displayString = I18n.translateToLocal("am2.gui.xpAlways") + ": " + ((doShowXPAlways) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
		showHudBars.displayString = I18n.translateToLocal("am2.gui.hudBars") + ": " + ((doShowBars) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));


		positiveBuffs.enabled = doShowBuffs;
		negativeBuffs.enabled = doShowBuffs;
		manaNumeric.enabled = doShowNumerics;
		burnoutNumeric.enabled = doShowNumerics;
		XPNumeric.enabled = doShowNumerics;

		manaButton.enabled = doShowBars;
		burnoutButton.enabled = doShowBars;

		armorHead.enabled = doShowArmor;
		armorChest.enabled = doShowArmor;
		armorLegs.enabled = doShowArmor;
		armorBoots.enabled = doShowArmor;
		manaShielding.enabled = doShowArmor;

		initButtonAndSnapData(manaButton, ArsMagica2.config.getManaHudPosition());
		initButtonAndSnapData(burnoutButton, ArsMagica2.config.getBurnoutHudPosition()); //new AMVector2(0.5, 0.5)
		initButtonAndSnapData(levelButton, ArsMagica2.config.getLevelPosition());
		initButtonAndSnapData(affinityButton, ArsMagica2.config.getAffinityPosition());
		initButtonAndSnapData(positiveBuffs, ArsMagica2.config.getPositiveBuffsPosition());
		initButtonAndSnapData(negativeBuffs, ArsMagica2.config.getNegativeBuffsPosition());
		initButtonAndSnapData(armorHead, ArsMagica2.config.getArmorPositionHead());
		initButtonAndSnapData(armorChest, ArsMagica2.config.getArmorPositionChest());
		initButtonAndSnapData(armorLegs, ArsMagica2.config.getArmorPositionLegs());
		initButtonAndSnapData(armorBoots, ArsMagica2.config.getArmorPositionBoots());
		initButtonAndSnapData(xpBar, ArsMagica2.config.getXPBarPosition());
		initButtonAndSnapData(contingency, ArsMagica2.config.getContingencyPosition());
		initButtonAndSnapData(manaNumeric, ArsMagica2.config.getManaNumericPosition());
		initButtonAndSnapData(burnoutNumeric, ArsMagica2.config.getBurnoutNumericPosition());
		initButtonAndSnapData(XPNumeric, ArsMagica2.config.getXPNumericPosition());
		initButtonAndSnapData(spellBook, ArsMagica2.config.getSpellBookPosition());
		initButtonAndSnapData(manaShielding, ArsMagica2.config.getManaShieldingPosition());

		setOptionsVisibility(false);

		this.buttonList.add(showBuffs);
		this.buttonList.add(showNumerics);
		this.buttonList.add(options);
		this.buttonList.add(showHudMinimally);
		this.buttonList.add(showArmorUI);
		this.buttonList.add(showXPAlways);
		this.buttonList.add(showHudBars);
	}

	private void setOptionsVisibility(boolean visible){
		showBuffs.visible = visible;
		showNumerics.visible = visible;
		showHudMinimally.visible = visible;
		showArmorUI.visible = visible;
		showXPAlways.visible = visible;
		showHudBars.visible = visible;
	}

	private void initButtonAndSnapData(GuiButtonVariableDims button, AMVector2 position){
		int xPos = (int)(position.x * screenWidth);
		int yPos = (int)(screenHeight * position.y);
		int snap = 0;

		if (xPos < width / 2)
			snap |= 0x1;

		if (yPos < height / 2)
			snap |= 0x2;

		button.setPosition(xPos, yPos);
		this.buttonList.add(button);
		snapData.put(button.id, snap);
	}

	private int getSnapData(GuiButtonVariableDims button){
		Integer i = snapData.get(button.id);
		return i != null ? i : 0;
	}

	private AMVector2 getSnapVector(GuiButtonVariableDims button){
		Integer snap = snapData.get(button.id);
		if (snap == null) snap = 0;
		float xPos = (float)((button.getPosition().x) / width);
		float yPos = (float)((button.getPosition().y) / height);

		return new AMVector2(xPos, yPos);
	}

	private void updateButtonPosition(GuiButtonVariableDims button, int newX, int newY){
		int centerX = screenWidth / 2;
		int centerY = screenHeight / 2;
		int snap = getSnapData(button);
		if (newX < centerX){
			snap |= 0x1;
		}else{
			snap &= ~0x1;
		}

		if (newY < centerY){
			snap |= 0x2;
		}else{
			snap &= ~0x2;
		}
		snapData.put(button.id, snap);
		button.setPosition(newX, newY);
	}

	@Override
	protected void mouseReleased(int par1, int par2, int par3){
		super.mouseReleased(par1, par2, par3);
		if (par3 == 1 || par3 == 0)
			dragTarget = null;
	}
	
	@Override
	protected void mouseClickMove(int par1, int par2, int par3, long par4){
		super.mouseClickMove(par1, par2, par3, par4);

		if (dragTarget != null){
			int newX = par1 - dragOffset.iX;
			int newY = par2 - dragOffset.iY;

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				newX = (int)(Math.round(newX / 5.0f) * 5.0f);
				newY = (int)(Math.round(newY / 5.0f) * 5.0f);
			}

			updateButtonPosition(dragTarget, newX, newY);
			storeGuiPositions();
		}else{
			dragTarget = null;
		}
	}

	private void storeGuiPositions(){
		ArsMagica2.config.setGuiPositions(
				getSnapVector(manaButton),
				getSnapVector(burnoutButton),
				getSnapVector(levelButton),
				getSnapVector(affinityButton),
				getSnapVector(positiveBuffs),
				getSnapVector(negativeBuffs),
				getSnapVector(armorHead),
				getSnapVector(armorChest),
				getSnapVector(armorLegs),
				getSnapVector(armorBoots),
				getSnapVector(xpBar),
				getSnapVector(contingency),
				getSnapVector(manaNumeric),
				getSnapVector(burnoutNumeric),
				getSnapVector(XPNumeric),
				getSnapVector(spellBook),
				getSnapVector(manaShielding),
				doShowBuffs,
				doShowNumerics,
				doShowHudMinimally,
				doShowArmor,
				doShowXPAlways,
				doShowBars);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) throws IOException{
		super.mouseClicked(par1, par2, par3);

		for (Object button : this.buttonList){
			if (button instanceof GuiButtonVariableDims){
				if (((GuiButtonVariableDims)button).mousePressed(mc, par1, par2)){
					if (button == showBuffs){
						doShowBuffs = !doShowBuffs;
						showBuffs.displayString = I18n.translateToLocal("am2.gui.buffTimers") + ": " + ((doShowBuffs) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						positiveBuffs.enabled = doShowBuffs;
						negativeBuffs.enabled = doShowBuffs;
						storeGuiPositions();
					}else if (button == showNumerics){
						doShowNumerics = !doShowNumerics;
						showNumerics.displayString = I18n.translateToLocal("am2.gui.numericValues") + ": " + ((doShowNumerics) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						manaNumeric.enabled = doShowNumerics;
						burnoutNumeric.enabled = doShowNumerics;
						XPNumeric.enabled = doShowNumerics;
						storeGuiPositions();
					}else if (button == showHudMinimally){
						doShowHudMinimally = !doShowHudMinimally;
						showHudMinimally.displayString = I18n.translateToLocal("am2.gui.minimalHud") + ": " + ((doShowHudMinimally) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						storeGuiPositions();
					}else if (button == options){
						showOptions = !showOptions;
						setOptionsVisibility(showOptions);
					}else if (button == showXPAlways){
						doShowXPAlways = !doShowXPAlways;
						showXPAlways.displayString = I18n.translateToLocal("am2.gui.xpAlways") + ": " + ((doShowXPAlways) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						storeGuiPositions();
					}else if (button == showHudBars){
						doShowBars = !doShowBars;
						showHudBars.displayString = I18n.translateToLocal("am2.gui.hudBars") + ": " + ((doShowBars) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						manaButton.enabled = doShowBars;
						burnoutButton.enabled = doShowBars;
						storeGuiPositions();
					}else if (button == showArmorUI){
						doShowArmor = !doShowArmor;
						showArmorUI.displayString = I18n.translateToLocal("am2.gui.armorUI") + ": " + ((doShowHudMinimally) ? I18n.translateToLocal("am2.gui.yes") : I18n.translateToLocal("am2.gui.no"));
						armorHead.enabled = doShowArmor;
						armorChest.enabled = doShowArmor;
						armorLegs.enabled = doShowArmor;
						armorBoots.enabled = doShowArmor;
						manaShielding.enabled = doShowArmor;
						storeGuiPositions();
					}else if (!showOptions){
						dragTarget = (GuiButtonVariableDims)button;
						AMVector2 buttonPos = ((GuiButtonVariableDims)button).getPosition();
						AMVector2 mousePos = new AMVector2(par1, par2);
						dragOffset = mousePos.subtract(buttonPos);
					}
				}
			}
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) throws IOException{
		if (par2 == Keyboard.KEY_ESCAPE){
			ArsMagica2.config.saveGuiPositions();
			if (showOptions){
				showOptions = false;
				setOptionsVisibility(false);
				return;
			}
		}
		super.keyTyped(par1, par2);
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3){
		super.drawScreen(par1, par2, par3);
		if (dragTarget != null){
			int snap = getSnapData(dragTarget);
			//x
			if ((snap & 0x1) == 0x1){
				drawSnapLeft(dragTarget);
			}else{
				drawSnapRight(dragTarget);
			}

			//y
			if ((snap & 0x2) == 0x2){
				drawSnapTop(dragTarget);
			}else{
				drawSnapBottom(dragTarget);
			}
		}
	}

	private void drawSnapLeft(GuiButtonVariableDims button){
		GL11.glColor3f(0, 1, 0);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(4f);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(0, 0, this.zLevel);
		GL11.glVertex3f(0, screenHeight, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(0, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(0, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(5, button.yPosition + button.getDimensions().iY / 2 - 5, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(0, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(5, button.yPosition + button.getDimensions().iY / 2 + 5, this.zLevel);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void drawSnapRight(GuiButtonVariableDims button){
		GL11.glColor3f(0, 1, 0);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(4f);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(screenWidth, 0, this.zLevel);
		GL11.glVertex3f(screenWidth, screenHeight, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(screenWidth, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(screenWidth, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(screenWidth - 5, button.yPosition + button.getDimensions().iY / 2 - 5, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(screenWidth, button.yPosition + button.getDimensions().iY / 2, this.zLevel);
		GL11.glVertex3f(screenWidth - 5, button.yPosition + button.getDimensions().iY / 2 + 5, this.zLevel);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void drawSnapTop(GuiButtonVariableDims button){
		GL11.glColor3f(0, 1, 0);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(4f);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(0, 0, this.zLevel);
		GL11.glVertex3f(screenWidth, 0, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, button.yPosition, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, 0, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, 0, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2 + 5, 5, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, 0, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2 - 5, 5, this.zLevel);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void drawSnapBottom(GuiButtonVariableDims button){
		GL11.glColor3f(0, 1, 0);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(4f);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(0, screenHeight, this.zLevel);
		GL11.glVertex3f(screenWidth, screenHeight, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, button.yPosition + button.getDimensions().iY, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, screenHeight, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, screenHeight, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2 + 5, screenHeight - 5, this.zLevel);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2, screenHeight, this.zLevel);
		GL11.glVertex3f(button.xPosition + button.getDimensions().iX / 2 - 5, screenHeight - 5, this.zLevel);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) throws IOException{
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void onGuiClosed(){
		ArsMagica2.config.saveGuiPositions();
		super.onGuiClosed();
	}
}
