package am2.utils;

import net.minecraft.util.math.Vec3d;

public class AMLineSegment{
	private Vec3d a, b;

	public AMLineSegment(Vec3d a, Vec3d b){
		this.a = a;
		this.b = b;
	}

	public Vec3d closestPointOnLine(Vec3d vPoint){

		// Create the vector from end point vA to our point vPoint.
		Vec3d vVector1 = vPoint.subtract(a);

		// Create a normalized direction vector from end point vA to end point vB
		Vec3d vVector2 = b.subtract(a).normalize();

		// Use the distance formula to find the distance of the line segment (or magnitude)
		float d = (float)a.distanceTo(b);

		// Using the dot product, we project the vVector1 onto the vector vVector2.
		// This essentially gives us the distance from our projected vector from vA.
		double t = vVector2.dotProduct(vVector1);

		// If our projected distance from vA, "t", is less than or equal to 0, it must
		// be closest to the end point vA.  We want to return this end point.
		if (t <= 0)
			return a;

		// If our projected distance from vA, "t", is greater than or equal to the magnitude
		// or distance of the line segment, it must be closest to the end point vB.  So, return vB.
		if (t >= d)
			return b;

		Vec3d vVector3 = vVector2.scale(t);
		Vec3d vClosestPoint = a.add(vVector3);

		return vClosestPoint;
	}
}
