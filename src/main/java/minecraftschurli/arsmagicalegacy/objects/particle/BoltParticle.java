package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoltParticle extends Particle {
    private final BoltParticleData main;
    private BoltParticleData.BoltType type;
    private int overrideColor = -1;

    public BoltParticle(World world, Vec3d jammervec, Vec3d targetvec, long seed) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(jammervec, targetvec, seed);
        setupFromMain();
    }

    public BoltParticle(World world, Entity detonator, Entity target, long seed) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(detonator, target, seed);
        setupFromMain();
    }

    public BoltParticle(World world, Entity detonator, Entity target, long seed, int speed) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(detonator, target, seed, speed);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(x1, y1, z1, x, y, z, seed, duration, multi);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(x1, y1, z1, x, y, z, seed, duration, multi, speed);
        setupFromMain();
    }

    public BoltParticle(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration) {
        super(world, 0, 0, 0, 0, 0, 0);
        main = new BoltParticleData(x1, y1, z1, x, y, z, seed, duration, 1);
        setupFromMain();
    }

    private static Vec3d getRelativeViewVector(Vec3d pos) {
        PlayerEntity renderentity = Minecraft.getInstance().player;
        return new Vec3d(renderentity.getPosX() - pos.x, renderentity.getPosY() - pos.y, renderentity.getPosZ() - pos.z);
    }

    private void setupFromMain() {
        age = main.getMaxAge();
        setPosition(main.getStart().x, main.getStart().y, main.getStart().z);
        motionX = motionY = motionZ = 0;
    }

    public void defaultFractal() {
        main.defaultFractal();
    }

    public void fractal(int splits, double amount, float splitchance, float splitlength, float splitangle) {
        main.fractal(splits, amount, splitchance, splitlength, splitangle);
    }

    public void finalizeBolt() {
        main.finalizeBolt();
    }

    public void setOverrideColor(int overrideColor) {
        this.overrideColor = overrideColor;
    }

    public void setSourceEntity(Entity entity) {
        main.wrapper = entity;
    }

    public void setType(BoltParticleData.BoltType type) {
        this.type = type;
    }

    public void setDamage(int dmg) {
        main.damage = dmg;
    }

    public void setNonLethal() {
        main.nonLethal = true;
    }

    public void setMultiplier(float m) {
        main.multiplier = m;
    }

    @Override
    public void tick() {
        main.onUpdate();
        if (main.getAge() >= main.getMaxAge()) setExpired();
    }

    private void renderBolt(IVertexBuilder buffer, float partialframe, float cosyaw, float cospitch, float sinyaw, float cossinpitch, int pass) {
        Vec3d playervec = new Vec3d(sinyaw * -cospitch, -cossinpitch / cosyaw, cosyaw * cospitch);
        float boltage = main.getAge() >= 0 ? 1 : 0;
        float mainalpha = 1;
        if (pass == 0) mainalpha = (1 - boltage) * 0.9F;
        else if (pass == 1) mainalpha = 1 - boltage * 0.6F;
        else mainalpha = 1 - boltage * 0.3F;
        int renderlength = (int) ((main.getAge() + partialframe + (int) (main.length * 3)) / (int) (main.length * 3) * main.numsegments0);
        for (BoltParticleData.Segment rendersegment : main.segments()) {
            if (rendersegment.segmentno <= renderlength) {
                float width = (float) (0.015F * (getRelativeViewVector(rendersegment.startpoint.point).length() / 10 + 1) * (1 + rendersegment.light));
                if (width > 0.05F) width = 0.05F;
                if (pass == 1) width += 0.025f;
                Vec3d diff1 = playervec.crossProduct(rendersegment.prevdiff).scale(width / rendersegment.sinprev);
                Vec3d diff2 = playervec.crossProduct(rendersegment.nextdiff).scale(width / rendersegment.sinnext);
                Vec3d startvec = rendersegment.startpoint.point;
                Vec3d endvec = rendersegment.endpoint.point;
                float rx1 = (float) (startvec.x - posX);
                float ry1 = (float) (startvec.y - posY);
                float rz1 = (float) (startvec.z - posZ);
                float rx2 = (float) (endvec.x - posX);
                float ry2 = (float) (endvec.y - posY);
                float rz2 = (float) (endvec.z - posZ);
                buffer.pos(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z).tex(0.5f, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z).tex(0.5f, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z).tex(0.5f, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                buffer.pos(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z).tex(0.5f, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                if (rendersegment.next == null) {
                    Vec3d roundend = RenderUtil.copyVec(rendersegment.endpoint.point).add(RenderUtil.copyVec(rendersegment.diff).normalize().scale(width));
                    float rx3 = (float) (roundend.x - posX);
                    float ry3 = (float) (roundend.y - posY);
                    float rz3 = (float) (roundend.z - posZ);
                    buffer.pos(rx3 - diff2.x, ry3 - diff2.y, rz3 - diff2.z).tex(0, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx2 - diff2.x, ry2 - diff2.y, rz2 - diff2.z).tex(0.5f, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx2 + diff2.x, ry2 + diff2.y, rz2 + diff2.z).tex(0.5f, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 + diff2.x, ry3 + diff2.y, rz3 + diff2.z).tex(0, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                }
                if (rendersegment.prev == null) {
                    Vec3d roundend = RenderUtil.copyVec(rendersegment.startpoint.point).subtract(RenderUtil.copyVec(rendersegment.diff)).normalize().scale(width);
                    float rx3 = (float) (roundend.x - posX);
                    float ry3 = (float) (roundend.y - posY);
                    float rz3 = (float) (roundend.z - posZ);
                    buffer.pos(rx1 - diff1.x, ry1 - diff1.y, rz1 - diff1.z).tex(0.5f, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 - diff1.x, ry3 - diff1.y, rz3 - diff1.z).tex(0, 0).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx3 + diff1.x, ry3 + diff1.y, rz3 + diff1.z).tex(0, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                    buffer.pos(rx1 + diff1.x, ry1 + diff1.y, rz1 + diff1.z).tex(0.5f, 1).color(particleRed, particleGreen, particleBlue, mainalpha * rendersegment.light).endVertex();
                }
            }
        }
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float v) {
        Tessellator tessellator = Tessellator.getInstance();
        PlayerEntity renderentity = Minecraft.getInstance().player;
        int visibleDistance = 100;
        if ((!Minecraft.getInstance().gameSettings.fancyGraphics)) visibleDistance = 50;
        if (renderentity.getDistanceSq(posX, posY, posZ) > visibleDistance) return;
        RenderSystem.pushMatrix();
        RenderSystem.pushTextureAttributes();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        particleRed = particleGreen = particleBlue = 1;
        switch (type) {
            case TYPE_0:
                particleRed = 0.1F;
                particleGreen = 0.1F;
                particleBlue = 0.6F;
                RenderSystem.blendFunc(770, 1);
                break;
            case TYPE_1:
                particleRed = 0.6F;
                particleGreen = 0.6F;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 1);
                break;
            case TYPE_2:
                particleRed = 0.6F;
                particleGreen = 0.1F;
                particleBlue = 0.6F;
                RenderSystem.blendFunc(770, 1);
                break;
            case TYPE_3:
                particleRed = 0.1F;
                particleGreen = 1;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 1);
                break;
            case TYPE_4:
                particleRed = 0.1F;
                particleGreen = 0.1F;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_5:
                particleRed = 0.6F;
                particleGreen = 0.3F;
                particleBlue = 0.6F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_6:
                particleRed = 0.1f;
                particleGreen = 0.1f;
                particleBlue = 0.1f;
                RenderSystem.blendFunc(770, 1);
                break;
        }
        if (overrideColor != -1) {
            particleRed = RenderUtil.getRed(overrideColor);
            particleGreen = RenderUtil.getGreen(overrideColor);
            particleBlue = RenderUtil.getBlue(overrideColor);
        }
        switch (type) {
            case TYPE_0:
                particleRed = 1;
                particleGreen = 0.6F;
                particleBlue = 1;
                break;
            case TYPE_1:
                particleRed = 0.1F;
                particleGreen = 0.1F;
                particleBlue = 1;
                break;
            case TYPE_2:
                particleRed = 0;
                particleGreen = 0;
                particleBlue = 0;
                break;
            case TYPE_3:
                particleRed = 0.1F;
                particleGreen = 0.6F;
                particleBlue = 0.1F;
                break;
            case TYPE_4:
                particleRed = 0.6F;
                particleGreen = 0.1F;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_5:
                particleRed = 1;
                particleGreen = 1;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_6:
                particleRed = 0.6f;
                particleGreen = 0.1f;
                particleBlue = 0.6f;
                RenderSystem.blendFunc(770, 1);
                break;
        }
        if (overrideColor != -1) {
            particleRed = RenderUtil.getRed(overrideColor);
            particleGreen = RenderUtil.getGreen(overrideColor);
            particleBlue = RenderUtil.getBlue(overrideColor);
        }
        switch (type) {
            case TYPE_0:
                particleRed = 1;
                particleGreen = 0.6F;
                particleBlue = 1;
                break;
            case TYPE_1:
                particleRed = 0.1F;
                particleGreen = 0.1F;
                particleBlue = 1;
                break;
            case TYPE_2:
                particleRed = 0;
                particleGreen = 0;
                particleBlue = 0;
                break;
            case TYPE_3:
                particleRed = 0.1F;
                particleGreen = 0.6F;
                particleBlue = 0.1F;
                break;
            case TYPE_4:
                particleRed = 0.1F;
                particleGreen = 0.1F;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_5:
                particleRed = 1;
                particleGreen = 1;
                particleBlue = 0.1F;
                RenderSystem.blendFunc(770, 771);
                break;
            case TYPE_6:
                particleRed = 0.6f;
                particleGreen = 0.1f;
                particleBlue = 0.6f;
                RenderSystem.blendFunc(770, 1);
                break;
        }
        if (overrideColor != -1) {
            particleRed = RenderUtil.getRed(overrideColor);
            particleGreen = RenderUtil.getGreen(overrideColor);
            particleBlue = RenderUtil.getBlue(overrideColor);
        }
        try {
            tessellator.draw();
        } catch (Throwable ignored) {
        }
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.popAttributes();
        RenderSystem.popMatrix();
    }

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}
