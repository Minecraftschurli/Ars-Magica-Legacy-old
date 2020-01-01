package am2.defs;

import am2.*;
import am2.utils.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class AMPotion extends Potion {
	
	int posX, posY = 0;

	public AMPotion(boolean badEffect, int potionColor) {
		super(badEffect, potionColor);
		if (!badEffect) this.setBeneficial();
	}
	
	@Override
	public Potion setIconIndex(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		return this;
	}
	
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.renderEngine.bindTexture(new ResourceLocation(ArsMagica2.MODID, "textures/gui/buffs_1.png"));
		float f = 1F/256F;
		GlStateManager.enableBlend();
		RenderUtils.drawBox(x + 6, y + 7, 18, 18, 0.5F, 18 * f * posY, 18 * f * posX, 18 * f * posY + 18 * f, 18 * f * posX + 18 * f);
	}
	
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		renderInventoryEffect(x - 3, y - 3, effect, mc);
	}

}
