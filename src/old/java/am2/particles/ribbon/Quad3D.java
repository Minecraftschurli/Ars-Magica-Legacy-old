package am2.particles.ribbon;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import am2.ArsMagica2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;

public class Quad3D{
	Vec3d p0, p1, p2, p3;
	Vec3d avg;
	private TextureAtlasSprite icon;
	private static final Random rand = new Random();

	Vec3d normal;

	Quad3D(Vec3d p0, Vec3d p1, Vec3d p2, Vec3d p3, TextureAtlasSprite icon){
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;

		avg = new Vec3d((p0.xCoord + p1.xCoord + p2.xCoord + p3.xCoord) / 4, (p0.yCoord + p1.yCoord + p2.yCoord + p3.yCoord) / 4D, (p0.zCoord + p1.zCoord + p2.zCoord + p3.zCoord) / 4D);

		this.icon = icon;

		calcNormal(p0, p1, p2);
	}

	void calcNormal(Vec3d v1, Vec3d v2, Vec3d v3){
		double Qx, Qy, Qz, Px, Py, Pz;

		Qx = v2.xCoord - v1.xCoord;
		Qy = v2.yCoord - v1.yCoord;
		Qz = v2.zCoord - v1.zCoord;

		Px = v3.xCoord - v1.xCoord;
		Py = v3.yCoord - v1.yCoord;
		Pz = v3.zCoord - v1.zCoord;

		normal = new Vec3d(Py * Qz - Pz * Qy, Pz * Qx - Px * Qz, Px * Qy - Py * Qx);
	}

	void draw(){
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		//noStroke();
		//GL11.glBegin(GL11.GL_QUADS);
		boolean wasTessellating = false;
		Tessellator t = Tessellator.getInstance();
		try{
			t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
		} catch (IllegalStateException e) {
			wasTessellating = true;
			t.draw();
			t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
		}
		t.getBuffer().pos(p0.xCoord, p0.yCoord, p0.zCoord).tex( icon.getMinU(), icon.getMinV()).endVertex();
		t.getBuffer().pos(p1.xCoord, p1.yCoord, p1.zCoord).tex( icon.getMaxU(), icon.getMinV()).endVertex();
		t.getBuffer().pos(p2.xCoord, p2.yCoord, p2.zCoord).tex( icon.getMaxU(), icon.getMaxV()).endVertex();
		t.getBuffer().pos(p3.xCoord, p3.yCoord, p3.zCoord).tex( icon.getMinU(), icon.getMaxV()).endVertex();
		t.draw();
		if (ArsMagica2.config.FullGFX()){
			double off = 0.005;

			t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
			//t.setBrightness(0xF00F0);
			GL11.glColor4f(0, 0.5f, 1.0f, 0.6f);
			t.getBuffer().pos(p0.xCoord + off, p0.yCoord + off, p0.zCoord + off).tex( icon.getMinU(), icon.getMinV()).endVertex();
			t.getBuffer().pos(p1.xCoord + off, p1.yCoord + off, p1.zCoord + off).tex( icon.getMaxU(), icon.getMinV()).endVertex();
			t.getBuffer().pos(p2.xCoord + off, p2.yCoord + off, p2.zCoord + off).tex( icon.getMaxU(), icon.getMaxV()).endVertex();
			t.getBuffer().pos(p3.xCoord + off, p3.yCoord + off, p3.zCoord + off).tex( icon.getMinU(), icon.getMaxV()).endVertex();
			t.draw();

			GL11.glColor4f(1, 1, 1, 0.6f);
		}
		if (wasTessellating)
			t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
		double mul = 0.0025;
		double halfMul = 0.00125;

		Vec3d vecOffset = new Vec3d(rand.nextDouble() * mul - halfMul, rand.nextDouble() * mul - halfMul, rand.nextDouble() * mul - halfMul);
		p0.add(vecOffset);
		p1.add(vecOffset);
		p2.add(vecOffset);
		p3.add(vecOffset);
	}
}