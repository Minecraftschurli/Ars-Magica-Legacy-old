package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

/**
 * @author Minecraftschurli
 * @version 2020-04-30
 */
public class CraftingAltarModel extends BakedModelWrapper<IBakedModel> {
    public static final String BLOCK_ATLAS = "minecraft:textures/atlas/blocks.png";
    public static final ResourceLocation OVERLAY_LOC = new ResourceLocation(ArsMagicaAPI.MODID, "block/altar_core_overlay");
    public static final ModelProperty<BlockState> CAMO_STATE = new ModelProperty<>();
    public static TextureAtlasSprite OVERLAY;

    public CraftingAltarModel(IBakedModel originalModel) {
        super(originalModel);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if (state != null && state.has(CraftingAltarBlock.FORMED) && state.get(CraftingAltarBlock.FORMED)) {
            BlockState camoState = extraData.getData(CAMO_STATE);
            if (camoState != null) {
                List<BakedQuad> quads = new ArrayList<>(Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(camoState).getQuads(camoState, side, rand, EmptyModelData.INSTANCE));
                if (!quads.isEmpty() && side == Direction.DOWN)
                    quads.add(new RetexturedBakedQuad(quads.get(0), OVERLAY));
                return quads;
            }
        }
        return super.getQuads(state, side, rand, extraData);
    }
}
