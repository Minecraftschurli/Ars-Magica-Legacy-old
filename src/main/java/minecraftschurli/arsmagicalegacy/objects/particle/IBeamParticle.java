package minecraftschurli.arsmagicalegacy.objects.particle;

public interface IBeamParticle {
    void setType(int type);
    void setColor(int color);
    void setColor(int r, int g, int b);
    void setColor(float r, float g, float b);
    void setFirstPersonPlayerCast();
}
