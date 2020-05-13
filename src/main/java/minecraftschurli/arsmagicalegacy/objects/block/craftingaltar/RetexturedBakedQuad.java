package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import java.util.Arrays;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RetexturedBakedQuad extends BakedQuad {

    private final TextureAtlasSprite texture;

    public RetexturedBakedQuad(BakedQuad quad, TextureAtlasSprite textureIn) {
        super(Arrays.copyOf(quad.getVertexData(), quad.getVertexData().length), quad.getTintIndex(), FaceBakery.getFacingFromVertexData(quad.getVertexData()), quad.func_187508_a(), quad.shouldApplyDiffuseLighting());
        texture = textureIn;
        remapQuad();
    }

    private static float getUnInterpolatedU(TextureAtlasSprite sprite, float u) {
        float f = sprite.getMaxU() - sprite.getMinU();
        return (u - sprite.getMinU()) / f * 16.0F;
    }

    private static float getUnInterpolatedV(TextureAtlasSprite sprite, float v) {
        float f = sprite.getMaxV() - sprite.getMinV();
        return (v - sprite.getMinV()) / f * 16.0F;
    }

    private void remapQuad() {
        for (int i = 0; i < 4; ++i) {
            int j = DefaultVertexFormats.BLOCK.getIntegerSize() * i;
            int uvIndex = 4;
            vertexData[j + uvIndex] = Float.floatToRawIntBits(texture.getInterpolatedU(getUnInterpolatedU(sprite, Float.intBitsToFloat(vertexData[j + uvIndex]))));
            vertexData[j + uvIndex + 1] = Float.floatToRawIntBits(texture.getInterpolatedV(getUnInterpolatedV(sprite, Float.intBitsToFloat(vertexData[j + uvIndex + 1]))));
        }
    }

    @Nonnull
    @Override
    public TextureAtlasSprite func_187508_a() {
        return texture;
    }
}
