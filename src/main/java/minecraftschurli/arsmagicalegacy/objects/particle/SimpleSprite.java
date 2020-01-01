package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

import java.util.*;

public class SimpleSprite extends TextureAtlasSprite implements IAnimatedSprite {
    protected SimpleSprite(ResourceLocation locationIn, int widthIn, int heightIn) {
        super(locationIn, widthIn, heightIn);
    }

    @Override
    public TextureAtlasSprite get(int particleAge, int particleMaxAge) {
        return null;
    }

    @Override
    public TextureAtlasSprite get(Random p_217590_1_) {
        return null;
    }
}
