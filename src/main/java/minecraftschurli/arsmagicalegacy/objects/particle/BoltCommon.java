package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class BoltCommon {
    ArrayList<Segment> segments;
    Vec3d start;
    Vec3d end;
    HashMap<Integer, Integer> splitparents;
    public float multiplier;
    public float length;
    public int numsegments0;
    public int increment;
    public int type = 0;
    public boolean nonLethal = false;
    private int numsplits;
    private boolean finalized;
    private Random rand;
    public long seed;
    public int particleAge;
    public int particleMaxAge;
    public int damage;
    public Entity wrapper;

    public BoltCommon(World world, Vec3d jammervec, Vec3d targetvec, long seed){
        this.segments = new ArrayList<>();
        this.splitparents = new HashMap<>();
        this.start = jammervec;
        this.end = targetvec;
        this.seed = seed;
        this.rand = new Random(seed);
        this.numsegments0 = 1;
        this.increment = 1;
        this.length = (float)this.end.subtract(this.start).length();
        this.particleMaxAge = 30;
        this.multiplier = 1.0F;
        this.particleAge = (-(int)(this.length * 3.0F));
        new AxisAlignedBB(Math.min(this.start.x, this.end.x), Math.min(this.start.y, this.end.y), Math.min(this.start.z, this.end.z), Math.max(this.start.x, this.end.x), Math.max(this.start.y, this.end.y), Math.max(this.start.z, this.end.z)).expand(this.length / 2.0F, this.length / 2.0F, this.length / 2.0F);
        this.segments.add(new Segment(this.start, this.end));
    }

    public BoltCommon(World world, Entity detonator, Entity target, long seed){
        this(world, detonator.getLookVec(), target.getLookVec(), seed);
    }

    public BoltCommon(World world, Entity detonator, Entity target, long seed, int speed){
        this(world, detonator.getLookVec(), new Vec3d(target.posX, target.posY + target.getEyeHeight() - 0.699999988079071D, target.posZ), seed);
        this.increment = speed;
        this.multiplier = 0.4F;
    }

    public BoltCommon(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi){
        this(world, new Vec3d(x1, y1, z1), new Vec3d(x, y, z), seed);
        this.particleMaxAge = (duration + this.rand.nextInt(duration) - duration / 2);
        this.multiplier = multi;
    }

    public BoltCommon(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed){
        this(world, new Vec3d(x1, y1, z1), new Vec3d(x, y, z), seed);
        this.particleMaxAge = (duration + this.rand.nextInt(duration) - duration / 2);
        this.multiplier = multi;
        this.increment = speed;
    }

    public void setMultiplier(float m){
        this.multiplier = m;
    }

    public void fractal(int splits, float amount, float splitchance, float splitlength, float splitangle){
        if (this.finalized)
            return;
        ArrayList<Segment> oldsegments = this.segments;
        this.segments = new ArrayList<>();
        Segment prev = null;
        for (Segment segment : oldsegments) {
            prev = segment.prev;
            Vec3d subsegment = segment.diff.scale(1.0F / splits);
            BoltPoint[] newpoints = new BoltPoint[splits + 1];
            Vec3d startpoint = segment.startpoint.point;
            newpoints[0] = segment.startpoint;
            newpoints[splits] = segment.endpoint;
            for (int i = 1; i < splits; i++) {
                Vec3d randoff = segment.diff.rotatePitch(this.rand.nextFloat() * 360.0F);
                randoff.scale((this.rand.nextFloat() - 0.5F) * amount);
                Vec3d basepoint = startpoint.add(subsegment.scale(i));
                newpoints[i] = new BoltPoint(basepoint, randoff);
            }
            for (int i = 0; i < splits; i++) {
                Segment next = new Segment(newpoints[i], newpoints[(i + 1)], segment.light, segment.segmentno * splits + i, segment.splitno);
                next.prev = prev;
                if (prev != null) prev.next = next;
                if ((i != 0) && (this.rand.nextFloat() < splitchance)) {
                    Vec3d splitrot = next.diff.rotatePitch(this.rand.nextFloat() * 360.0F);
                    Vec3d diff = next.diff.rotatePitch((this.rand.nextFloat() * 0.66F + 0.33F) * splitangle).scale(splitlength);
                    this.numsplits += 1;
                    this.splitparents.put(this.numsplits, next.splitno);
                    Segment split = new Segment(newpoints[i], new BoltPoint(newpoints[(i + 1)].basepoint, newpoints[(i + 1)].offsetvec.add(diff)), segment.light / 2.0F, next.segmentno, this.numsplits);
                    split.prev = prev;
                    this.segments.add(split);
                }
                prev = next;
                this.segments.add(next);
            }
            if (segment.next != null) segment.next.prev = prev;
        }
        this.numsegments0 *= splits;
    }

    public void defaultFractal(){
        fractal(2, this.length * this.multiplier / 8.0F, 0.7F, 0.1F, 45.0F);
        fractal(2, this.length * this.multiplier / 12.0F, 0.5F, 0.1F, 50.0F);
        fractal(2, this.length * this.multiplier / 17.0F, 0.5F, 0.1F, 55.0F);
        fractal(2, this.length * this.multiplier / 23.0F, 0.5F, 0.1F, 60.0F);
        fractal(2, this.length * this.multiplier / 30.0F, 0.0F, 0.0F, 0.0F);
        fractal(2, this.length * this.multiplier / 34.0F, 0.0F, 0.0F, 0.0F);
//        if (ArsMagicaLegacy.config.LowGFX()) fractal(2, this.length * this.multiplier / 40.0F, 0.0F, 0.0F, 0.0F);
//        if (ArsMagicaLegacy.config.FullGFX()){
//            fractal(2, this.length * this.multiplier / 8.0F, 0.7F, 0.1F, 45.0F);
//            fractal(2, this.length * this.multiplier / 8.0F, 0.7F, 0.1F, 45.0F);
//        }
    }

    private void calculateCollisionAndDiffs(){
        HashMap<Integer, Integer> lastactivesegment = new HashMap<>();
        Collections.sort(this.segments, new SegmentSorter());
        int lastsplitcalc = 0;
        int lastactiveseg = 0;
        float splitresistance = 0.0F;
        for (Iterator<Segment> iterator = this.segments.iterator(); iterator.hasNext(); ){
            Segment segment = iterator.next();
            if (segment.splitno > lastsplitcalc){
                lastactivesegment.put(lastsplitcalc, lastactiveseg);
                lastsplitcalc = segment.splitno;
                lastactiveseg = lastactivesegment.get(this.splitparents.get(segment.splitno));
                splitresistance = lastactiveseg >= segment.segmentno ? 0.0F : 50.0F;
            }
            if (splitresistance < 40.0F * segment.light) lastactiveseg = segment.segmentno;
        }
        lastactivesegment.put(lastsplitcalc, lastactiveseg);
        lastsplitcalc = 0;
        lastactiveseg = lastactivesegment.get(0);
        Segment segment;
        for (Iterator<Segment> iterator = this.segments.iterator(); iterator.hasNext(); segment.calcEndDiffs()){
            segment = iterator.next();
            if (lastsplitcalc != segment.splitno){
                lastsplitcalc = segment.splitno;
                lastactiveseg = lastactivesegment.get(segment.splitno);
            }
            if (segment.segmentno > lastactiveseg) iterator.remove();
        }
    }

    public void finalizeBolt(){
        if (this.finalized) return;
        this.finalized = true;
        calculateCollisionAndDiffs();
        Collections.sort(this.segments, new SegmentLightSorter());
    }

    public void onUpdate(){
        this.particleAge += this.increment;
        if (this.particleAge > this.particleMaxAge) this.particleAge = this.particleMaxAge;
    }

    public class SegmentSorter implements Comparator<Segment>{
        final BoltCommon bolt;

        public int compare(BoltCommon.Segment o1, BoltCommon.Segment o2){
            int comp = Integer.compare(o1.splitno, o2.splitno);
            if (comp == 0) return Integer.compare(o1.segmentno, o2.segmentno);
            return comp;
        }

        public int compare(Segment obj, Object obj1){
            return compare(obj, (BoltCommon.Segment)obj1);
        }

        public SegmentSorter(){
            this.bolt = BoltCommon.this;
        }
    }

    public class SegmentLightSorter implements Comparator<Segment>{
        final BoltCommon bolt;

        public int compare(BoltCommon.Segment o1, BoltCommon.Segment o2){
            return Float.compare(o2.light, o1.light);
        }

        public int compare(Segment obj, Object obj1){
            return compare((BoltCommon.Segment)obj, (BoltCommon.Segment)obj1);
        }

        public SegmentLightSorter(){
            this.bolt = BoltCommon.this;
        }
    }

    public class Segment {
        public BoltCommon.BoltPoint startpoint;
        public BoltCommon.BoltPoint endpoint;
        public Vec3d diff;
        public Segment prev;
        public Segment next;
        public Vec3d nextdiff;
        public Vec3d prevdiff;
        public float sinprev;
        public float sinnext;
        public float light;
        public int segmentno;
        public int splitno;
        public void calcDiff(){
            this.diff = this.endpoint.point.subtract(this.startpoint.point);
        }

        public void calcEndDiffs(){
            if (this.prev != null){
                Vec3d prevdiffnorm = this.prev.diff.normalize();
                Vec3d thisdiffnorm = this.diff.normalize();
                this.prevdiff = thisdiffnorm.add(prevdiffnorm).normalize();
//                this.sinprev = (float)Math.sin(Vec3d.anglePreNorm(thisdiffnorm, prevdiffnorm.scale(-1.0F)) / 2.0F);
            } else {
                this.prevdiff = this.diff.normalize();
                this.sinprev = 1.0F;
            }
            if (this.next != null){
                Vec3d nextdiffnorm = this.next.diff.normalize();
                Vec3d thisdiffnorm = this.diff.normalize();
                this.nextdiff = thisdiffnorm.add(nextdiffnorm).normalize();
//                this.sinnext = (float)Math.sin(Vec3d.anglePreNorm(thisdiffnorm, nextdiffnorm.scale(-1.0F)) / 2.0F);
            } else {
                this.nextdiff = this.diff.normalize();
                this.sinnext = 1.0F;
            }
        }

        public String toString(){
            return this.startpoint.point.toString() + " " + this.endpoint.point.toString();
        }

        public Segment(BoltCommon.BoltPoint start, BoltCommon.BoltPoint end, float light, int segmentnumber, int splitnumber){
            this.startpoint = start;
            this.endpoint = end;
            this.light = light;
            this.segmentno = segmentnumber;
            this.splitno = splitnumber;
            calcDiff();
        }

        public Segment(Vec3d start, Vec3d end){
            this(new BoltCommon.BoltPoint(start, new Vec3d(0.0D, 0.0D, 0.0D)), new BoltCommon.BoltPoint(end, new Vec3d(0.0D, 0.0D, 0.0D)), 1.0F, 0, 0);
        }
    }

    public class BoltPoint{
        Vec3d point;
        Vec3d basepoint;
        Vec3d offsetvec;

        public BoltPoint(Vec3d basepoint, Vec3d offsetvec){
            this.point = basepoint.add(offsetvec);
            this.basepoint = basepoint;
            this.offsetvec = offsetvec;
        }
    }
}
