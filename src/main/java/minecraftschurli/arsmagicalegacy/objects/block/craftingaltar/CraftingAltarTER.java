package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-28
 */
public class CraftingAltarTER extends TileEntityRenderer<CraftingAltarTileEntity> {
    public CraftingAltarTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CraftingAltarTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        if (world == null) return;
        BlockState camoState = tileEntityIn.getCamoState();
        if (camoState == null) return;
        matrixStackIn.push();
        Minecraft.getInstance()
                .getBlockRendererDispatcher()
                .renderModel(
                        camoState,
                        tileEntityIn.getPos(),
                        world,
                        matrixStackIn,
                        bufferIn.getBuffer(RenderTypeLookup.getRenderType(camoState)),
                        EmptyModelData.INSTANCE
                );
        matrixStackIn.pop();
    }
}
