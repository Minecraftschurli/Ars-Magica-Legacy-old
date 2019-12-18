package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.world.*;

public abstract class ParticleController{
    protected StandardParticle particle;
    protected int priority;
    protected boolean exclusive;
    private boolean finished;
    private boolean killParticleOnFinish;
    protected boolean firstTick = true;
    public static String[] auraControllerOptions = new String[]{"fade", "float", "sink", "orbit", "arc", "flee", "forward", "pendulum", "grow"};

    public ParticleController(StandardParticle particleEffect, int priority, boolean exclusive){
        this.particle = particleEffect;
        this.priority = priority;
        this.exclusive = exclusive;
        this.killParticleOnFinish = false;
    }

    protected ParticleController targetNewParticle(StandardParticle particle){
        if (this.particle != null) this.particle.removeController(this);
        particle.addController(this);
        this.particle = particle;
        return this;
    }

    public ParticleController setKillParticleOnFinish(boolean kill){
        this.killParticleOnFinish = kill;
        return this;
    }

    public boolean getKillParticleOnFinish(){
        return this.killParticleOnFinish;
    }

    public abstract void doUpdate();

    @Override
    public abstract ParticleController clone();

    public void onUpdate(World world){
        if (!world.isRemote){
            if (particle != null) particle.setExpired();
            return;
        }
        if (particle != null) doUpdate();
        if (firstTick) firstTick = false;
    }

    public int getPriority(){
        return priority;
    }

    protected void finish(){
        this.finished = true;
        if (killParticleOnFinish && particle != null) particle.setExpired();
    }

    public boolean getExclusive(){
        return exclusive;
    }

    public boolean getFinished(){
        return finished;
    }
}
