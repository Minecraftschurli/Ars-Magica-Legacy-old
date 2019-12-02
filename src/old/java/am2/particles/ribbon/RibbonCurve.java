package am2.particles.ribbon;

import java.util.LinkedList;

import am2.particles.AMParticleIcons;
import net.minecraft.util.math.Vec3d;

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
		int size = quads.size();
		for (int i = 0; i < size; i++){
			Quad3D q = quads.get(i);
			q.draw();
		}
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

		Quad3D q = new Quad3D(p0, p1, p2, p3, AMParticleIcons.instance.getIconByName("symbols"));
		quads.add(q);
	}

	/**
	 * Given a bezier curve defined by 3 points, an offset distance (k) and a time (t), returns an Vec3d
	 */

	Vec3d getOffsetPoint(float t, float k){
		Vec3d p0 = startPt;
		Vec3d p1 = controlPt;
		Vec3d p2 = endPt;

		//-- x(t), y(t)
		double xt = (1 - t) * (1 - t) * p0.xCoord + 2 * t * (1 - t) * p1.xCoord + t * t * p2.xCoord;
		double yt = (1 - t) * (1 - t) * p0.yCoord + 2 * t * (1 - t) * p1.yCoord + t * t * p2.yCoord;
		double zt = (1 - t) * (1 - t) * p0.zCoord + 2 * t * (1 - t) * p1.zCoord + t * t * p2.zCoord;

		//-- x'(t), y'(t)
		double xd = t * (p0.xCoord - 2 * p1.xCoord + p2.xCoord) - p0.xCoord + p1.xCoord;
		double yd = t * (p0.yCoord - 2 * p1.yCoord + p2.yCoord) - p0.yCoord + p1.yCoord;
		double zd = t * (p0.zCoord - 2 * p1.zCoord + p2.zCoord) - p0.zCoord + p1.zCoord;
		double dd = (float)Math.pow(xd * xd + yd * yd + zd * zd, 1 / 3);

		return new Vec3d(xt + (k * yd) / dd, yt - (k * xd) / dd, zt - (k * xd) / dd);

	}
}
