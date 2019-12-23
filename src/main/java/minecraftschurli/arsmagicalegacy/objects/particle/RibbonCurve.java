package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.util.math.*;

import java.util.*;

public class RibbonCurve{
    float ribbonWidth;
    float resolution;
    Vec3d startPt, endPt, controlPt;
    int stepId;
    float ribbonColor = 0;
    LinkedList<Quad3D> quads;

    RibbonCurve(Vec3d pStartPt, Vec3d pEndPt, Vec3d pControlPt, float pwidth, float presolution, float pcolor){
        startPt = pStartPt;
        endPt = pEndPt;
        controlPt = pControlPt;
        resolution = presolution;
        ribbonWidth = pwidth;
        stepId = 0;
        ribbonColor = pcolor;
        quads = new LinkedList<>();
    }

    void draw(){
        for (Quad3D q : quads) q.draw();
    }

    void removeSegment(){
        if (quads.size() > 1) quads.removeFirst();
    }

    void addSegment(){
        float t = stepId / resolution;
        Vec3d p0 = getOffsetPoint(t, 0);
        Vec3d p3 = getOffsetPoint(t, ribbonWidth);
        stepId++;
        if (stepId > resolution) return;
        t = stepId / resolution;
        Vec3d p1 = getOffsetPoint(t, 0);
        Vec3d p2 = getOffsetPoint(t, ribbonWidth);
//        Quad3D q = new Quad3D(p0, p1, p2, p3, AMParticleIcons.instance.getIconByName("symbols"));
//        quads.add(q);
    }

    Vec3d getOffsetPoint(float t, float k){
        Vec3d p0 = startPt;
        Vec3d p1 = controlPt;
        Vec3d p2 = endPt;
        double xt = (1 - t) * (1 - t) * p0.getX() + 2 * t * (1 - t) * p1.getX() + t * t * p2.getX();
        double yt = (1 - t) * (1 - t) * p0.getY() + 2 * t * (1 - t) * p1.getY() + t * t * p2.getY();
        double zt = (1 - t) * (1 - t) * p0.getZ() + 2 * t * (1 - t) * p1.getZ() + t * t * p2.getZ();
        double xd = t * (p0.getX() - 2 * p1.getX() + p2.getX()) - p0.getX() + p1.getX();
        double yd = t * (p0.getY() - 2 * p1.getY() + p2.getY()) - p0.getY() + p1.getY();
        double zd = t * (p0.getZ() - 2 * p1.getZ() + p2.getZ()) - p0.getZ() + p1.getZ();
        double dd = (float)Math.pow(xd * xd + yd * yd + zd * zd, 1 / 3);
        return new Vec3d(xt + (k * yd) / dd, yt - (k * xd) / dd, zt - (k * xd) / dd);
    }
}