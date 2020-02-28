package minecraftschurli.arsmagicalegacy.objects.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minecraftschurli.arsmagicalegacy.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BoltParticleData implements IParticleData {
    public static final IParticleData.IDeserializer<BoltParticleData> DESERIALIZER = new IParticleData.IDeserializer<BoltParticleData>() {
        public BoltParticleData deserialize(ParticleType<BoltParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float)reader.readDouble();
            reader.expect(' ');
            float f1 = (float)reader.readDouble();
            reader.expect(' ');
            float f2 = (float)reader.readDouble();
            reader.expect(' ');
            float f3 = (float)reader.readDouble();
            return new BoltParticleData(particleTypeIn, f, f1, f2, f3);
        }

        public BoltParticleData read(ParticleType<BoltParticleData> particleTypeIn, PacketBuffer buffer) {
            return new BoltParticleData(particleTypeIn, buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
//    private final ParticleType<BoltParticleData> type;
    private boolean finalized;
    private int age;
    private int maxAge;
    private int numsplits;
    private Random rand;
    private ArrayList<Segment> segments;
    private HashMap<Integer, Integer> splitparents;
    private Vec3d start;
    private Vec3d end;
    public boolean nonLethal = false;
    public int damage;
    public int increment;
    public int numsegments0;
    public float multiplier;
    public double length;
    public Entity wrapper;

    public BoltParticleData(ParticleType<BoltParticleData> particleTypeIn, float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4);
//        type = particleTypeIn;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeFloat(alpha);
    }

    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), red, green, blue, alpha);
    }

    public ParticleType<BoltParticleData> getType() {
        return null;//type;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return alpha;
    }

    public Vec3d getStart() {
        return start;
    }

    public Vec3d getEnd() {
        return end;
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public BoltParticleData(Vec3d jammervec, Vec3d targetvec, long seed){
        segments = new ArrayList<>();
        splitparents = new HashMap<>();
        start = jammervec;
        end = targetvec;
        rand = new Random(seed);
        numsegments0 = 1;
        increment = 1;
        length = RenderUtils.copyVec(end).subtract(start).length();
        maxAge = 30;
        multiplier = 1;
        age = (int)-length * 3;
        new AxisAlignedBB(Math.min(start.x, end.x), Math.min(start.y, end.y), Math.min(start.z, end.z), Math.max(start.x, end.x), Math.max(start.y, end.y), Math.max(start.z, end.z)).expand(length / 2, length / 2, length / 2);
        segments.add(new Segment(start, end));
        red = 1;
        green = 1;
        blue = 1;
        alpha = 1;
    }

    public BoltParticleData(Entity detonator, Entity target, long seed){
        this(detonator.getLookVec(), target.getLookVec(), seed);
    }

    public BoltParticleData(Entity detonator, Entity target, long seed, int speed){
        this(detonator.getLookVec(), new Vec3d(target.getPosX(), target.getPosY() + target.getEyeHeight() - 0.699999988079071d, target.getPosZ()), seed);
        increment = speed;
        multiplier = 0.4F;
    }

    public BoltParticleData(double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi){
        this(new Vec3d(x1, y1, z1), new Vec3d(x, y, z), seed);
        maxAge = (duration + rand.nextInt(duration) - duration / 2);
        multiplier = multi;
    }

    public BoltParticleData(double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed){
        this(new Vec3d(x1, y1, z1), new Vec3d(x, y, z), seed);
        maxAge = (duration + rand.nextInt(duration) - duration / 2);
        multiplier = multi;
        increment = speed;
    }
    
    public enum BoltType {
        TYPE_0,
        TYPE_1,
        TYPE_2,
        TYPE_3,
        TYPE_4,
        TYPE_5,
        TYPE_6
    }
    
    public void setMultiplier(float m){
        multiplier = m;
    }

    public void fractal(int splits, double amount, float splitchance, float splitlength, float splitangle){
        if (finalized) return;
        ArrayList<Segment> oldsegments = segments;
        segments = new ArrayList<>();
        Segment prev;
        for (Segment segment : oldsegments) {
            prev = segment.prev;
            Vec3d subsegment = RenderUtils.copyVec(segment.diff).scale(1 / splits);
            BoltPoint[] newpoints = new BoltPoint[splits + 1];
            Vec3d startpoint = segment.startpoint.point;
            newpoints[0] = segment.startpoint;
            newpoints[splits] = segment.endpoint;
            for (int i = 1; i < splits; i++) {
                Vec3d randoff = segment.diff.rotatePitch(rand.nextFloat() * 360);
                randoff.scale((rand.nextFloat() - 0.5F) * amount);
                Vec3d basepoint = RenderUtils.copyVec(startpoint).add(RenderUtils.copyVec(subsegment).scale(i));
                newpoints[i] = new BoltPoint(basepoint, randoff);
            }
            for (int i = 0; i < splits; i++) {
                Segment next = new Segment(newpoints[i], newpoints[(i + 1)], segment.light, segment.segmentno * splits + i, segment.splitno);
                next.prev = prev;
                if (prev != null) prev.next = next;
                if ((i != 0) && (rand.nextFloat() < splitchance)) {
                    Vec3d diff = RenderUtils.copyVec(next.diff).rotatePitch((rand.nextFloat() * 0.66F + 0.33F) * splitangle).scale(splitlength);
                    numsplits += 1;
                    splitparents.put(numsplits, next.splitno);
                    Segment split = new Segment(newpoints[i], new BoltPoint(newpoints[(i + 1)].basepoint, RenderUtils.copyVec(newpoints[(i + 1)].offsetvec).add(diff)), segment.light / 2, next.segmentno, numsplits);
                    split.prev = prev;
                    segments.add(split);
                }
                prev = next;
                segments.add(next);
            }
            if (segment.next != null) segment.next.prev = prev;
        }
        numsegments0 *= splits;
    }

    public void defaultFractal(){
        fractal(2, length * multiplier / 8, 0.7F, 0.1F, 45);
        fractal(2, length * multiplier / 12, 0.5F, 0.1F, 50);
        fractal(2, length * multiplier / 17, 0.5F, 0.1F, 55);
        fractal(2, length * multiplier / 23, 0.5F, 0.1F, 60);
        fractal(2, length * multiplier / 30, 0, 0, 0);
        fractal(2, length * multiplier / 34, 0, 0, 0);
        fractal(2, length * multiplier / 8, 0.7F, 0.1F, 45);
        fractal(2, length * multiplier / 8, 0.7F, 0.1F, 45);
    }

    private void calculateCollisionAndDiffs(){
        HashMap<Integer, Integer> lastactivesegment = new HashMap<>();
        segments.sort(new SegmentSorter());
        int lastsplitcalc = 0;
        int lastactiveseg = 0;
        float splitresistance = 0;
        for (Segment segment : segments) {
            if (segment.splitno > lastsplitcalc) {
                lastactivesegment.put(lastsplitcalc, lastactiveseg);
                lastsplitcalc = segment.splitno;
                lastactiveseg = lastactivesegment.get(splitparents.get(segment.splitno));
                splitresistance = lastactiveseg >= segment.segmentno ? 0 : 50;
            }
            if (splitresistance < 40 * segment.light) lastactiveseg = segment.segmentno;
        }

        lastactivesegment.put(lastsplitcalc, lastactiveseg);
        lastsplitcalc = 0;
        lastactiveseg = lastactivesegment.get(0);
        Segment segment;
        for (Iterator<Segment> iterator = segments.iterator(); iterator.hasNext(); segment.calcEndDiffs()){
            segment = iterator.next();
            if (lastsplitcalc != segment.splitno){
                lastsplitcalc = segment.splitno;
                lastactiveseg = lastactivesegment.get(segment.splitno);
            }
            if (segment.segmentno > lastactiveseg) iterator.remove();
        }
    }

    public void finalizeBolt(){
        if (finalized) return;
        finalized = true;
        calculateCollisionAndDiffs();
        segments.sort(new SegmentLightSorter());
    }

    public void onUpdate(){
        age += increment;
        if (age > maxAge) age = maxAge;
    }

    public class SegmentSorter implements Comparator<Segment>{
        final BoltParticleData this$0;

        public int compare(BoltParticleData.Segment o1, BoltParticleData.Segment o2){
            int comp = Integer.compare(o1.splitno, o2.splitno);
            return comp == 0 ? Integer.compare(o1.segmentno, o2.segmentno) : comp;
        }

        public int compare(Segment s, Object o){
            return compare(s, (BoltParticleData.Segment)o);
        }

        public SegmentSorter(){
            this$0 = BoltParticleData.this;
        }
    }

    public class SegmentLightSorter
            implements Comparator<Segment>{
        final BoltParticleData this$0;

        public int compare(BoltParticleData.Segment o1, BoltParticleData.Segment o2){
            return Float.compare(o2.light, o1.light);
        }

        public int compare(Segment s, Object o){
            return compare(s, (BoltParticleData.Segment)o);
        }

        public SegmentLightSorter(){
            this$0 = BoltParticleData.this;
        }
    }

    public class Segment{
        public BoltParticleData.BoltPoint startpoint;
        public BoltParticleData.BoltPoint endpoint;
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
            diff = RenderUtils.copyVec(endpoint.point).subtract(startpoint.point);
        }

        public void calcEndDiffs(){
            if (prev != null){
                Vec3d prevdiffnorm = RenderUtils.copyVec(prev.diff).normalize();
                Vec3d thisdiffnorm = RenderUtils.copyVec(diff).normalize();
                prevdiff = thisdiffnorm.add(prevdiffnorm).normalize();
//                sinprev = (float)Math.sin(Vec3d.anglePreNorm(thisdiffnorm, prevdiffnorm.scale(-1)) / 2);
            }else{
                prevdiff = RenderUtils.copyVec(diff).normalize();
                sinprev = 1;
            }
            if (next != null){
                Vec3d nextdiffnorm = RenderUtils.copyVec(next.diff).normalize();
                Vec3d thisdiffnorm = RenderUtils.copyVec(diff).normalize();
                nextdiff = thisdiffnorm.add(nextdiffnorm).normalize();
//                sinnext = (float)Math.sin(Vec3d.anglePreNorm(thisdiffnorm, nextdiffnorm.scale(-1)) / 2);
            }else{
                nextdiff = RenderUtils.copyVec(diff).normalize();
                sinnext = 1;
            }
        }

        public String toString(){
            return startpoint.point.toString() + " " + endpoint.point.toString();
        }

        public Segment(BoltParticleData.BoltPoint start, BoltParticleData.BoltPoint end, float light, int segmentnumber, int splitnumber){
            startpoint = start;
            endpoint = end;
            this.light = light;
            segmentno = segmentnumber;
            splitno = splitnumber;
            calcDiff();
        }

        public Segment(Vec3d start, Vec3d end){
            this(new BoltParticleData.BoltPoint(start, new Vec3d(0, 0, 0)), new BoltParticleData.BoltPoint(end, new Vec3d(0, 0, 0)), 1, 0, 0);
        }
    }

    public class BoltPoint {
        Vec3d point;
        Vec3d basepoint;
        Vec3d offsetvec;

        public BoltPoint(Vec3d basepoint, Vec3d offsetvec){
            point = new Vec3d(basepoint.x, basepoint.y, basepoint.z).add(offsetvec);
            this.basepoint = basepoint;
            this.offsetvec = offsetvec;
        }
    }

    public List<Segment> segments() {
        return segments;
    }
}
