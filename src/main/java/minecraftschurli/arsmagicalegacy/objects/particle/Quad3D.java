package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class Quad3D {
    private static final Random rand = new Random();
    Vec3d p0, p1, p2, p3;
    Vec3d avg;
    Vec3d normal;
    private TextureAtlasSprite icon;

    Quad3D(Vec3d p0, Vec3d p1, Vec3d p2, Vec3d p3, TextureAtlasSprite icon) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        avg = new Vec3d((p0.getX() + p1.getX() + p2.getX() + p3.getX()) / 4, (p0.getY() + p1.getY() + p2.getY() + p3.getY()) / 4D, (p0.getZ() + p1.getZ() + p2.getZ() + p3.getZ()) / 4D);
        this.icon = icon;
        calcNormal(p0, p1, p2);
    }

    void calcNormal(Vec3d v1, Vec3d v2, Vec3d v3) {
        double Qx, Qy, Qz, Px, Py, Pz;
        Qx = v2.getX() - v1.getX();
        Qy = v2.getY() - v1.getY();
        Qz = v2.getZ() - v1.getZ();
        Px = v3.getX() - v1.getX();
        Py = v3.getY() - v1.getY();
        Pz = v3.getZ() - v1.getZ();
        normal = new Vec3d(Py * Qz - Pz * Qy, Pz * Qx - Px * Qz, Px * Qy - Py * Qx);
    }

    void draw() {
//        Minecraft.getInstance().gameRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        boolean wasTessellating = false;
        Tessellator t = Tessellator.getInstance();
        try {
            t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        } catch (IllegalStateException e) {
            wasTessellating = true;
            t.draw();
            t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        }
        t.getBuffer().pos(p0.getX(), p0.getY(), p0.getZ()).tex(icon.getMinU(), icon.getMinV()).endVertex();
        t.getBuffer().pos(p1.getX(), p1.getY(), p1.getZ()).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        t.getBuffer().pos(p2.getX(), p2.getY(), p2.getZ()).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        t.getBuffer().pos(p3.getX(), p3.getY(), p3.getZ()).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        t.draw();
//        if (ArsMagicaLegacy.config.FullGFX()){
        double off = 0.005;
        t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        GL11.glColor4f(0, 0.5f, 1.0f, 0.6f);
        t.getBuffer().pos(p0.getX() + off, p0.getY() + off, p0.getZ() + off).tex(icon.getMinU(), icon.getMinV()).endVertex();
        t.getBuffer().pos(p1.getX() + off, p1.getY() + off, p1.getZ() + off).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        t.getBuffer().pos(p2.getX() + off, p2.getY() + off, p2.getZ() + off).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        t.getBuffer().pos(p3.getX() + off, p3.getY() + off, p3.getZ() + off).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        t.draw();
        GL11.glColor4f(1, 1, 1, 0.6f);
//        }
        if (wasTessellating) t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
        double mul = 0.0025;
        double halfMul = 0.00125;
        Vec3d vecOffset = new Vec3d(rand.nextDouble() * mul - halfMul, rand.nextDouble() * mul - halfMul, rand.nextDouble() * mul - halfMul);
        p0.add(vecOffset);
        p1.add(vecOffset);
        p2.add(vecOffset);
        p3.add(vecOffset);
    }
}