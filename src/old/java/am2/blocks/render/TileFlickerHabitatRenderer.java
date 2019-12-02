/**
 *
 */
package am2.blocks.render;

import org.lwjgl.opengl.GL11;

import am2.blocks.tileentity.TileEntityFlickerHabitat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * @author Zero
 */
public class TileFlickerHabitatRenderer extends TileEntitySpecialRenderer<TileEntityFlickerHabitat>{

	private IModel modelFrame;
	private IBakedModel bakedModelFrame;
	private IModel modelCrystal;
	private IBakedModel bakedModelCrystal;
	
	private void createModels() {
		try {
			modelFrame = ModelLoaderRegistry.getModel(new ResourceLocation("arsmagica2", "block/flicker_habitat_frame.obj"));
			modelCrystal = ModelLoaderRegistry.getModel(new ResourceLocation("arsmagica2", "block/flicker_habitat_crystal.obj"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		bakedModelFrame = modelFrame.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
		bakedModelCrystal = modelCrystal.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
	}

	public void renderTileEntityAt(TileEntityFlickerHabitat tileentity, double x, double y, double z, float partialTicks, int destroyStage){
		createModels();
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		try{
			GlStateManager.pushMatrix();
			GlStateManager.translate(-tileentity.getPos().getX(), -tileentity.getPos().getY(), -tileentity.getPos().getZ());
			Tessellator tesselator = Tessellator.getInstance();
			tesselator.getBuffer().begin(7, DefaultVertexFormats.BLOCK);
			Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), bakedModelFrame, getWorld().getBlockState(tileentity.getPos()), tileentity.getPos(), tesselator.getBuffer(), false);
			tesselator.draw();
			GlStateManager.popMatrix();
		}catch (Throwable t){
		}

		if (tileentity.hasFlicker()){

			if (tileentity.isUpgrade()){
				switch (tileentity.getMainHabitatDirection()){
				case DOWN:
					GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(0.0f, -0.9f, 0.0f);
					break;
				case EAST:
					GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(-0.5f, -0.45f, 0.0f);
					break;
				case NORTH:
					GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
					GL11.glTranslatef(0.0f, -0.45f, 0.5f);
					break;
				case SOUTH:
					GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
					GL11.glTranslatef(0.0f, -0.45f, -0.5f);
					break;
				case UP:
					break;
				case WEST:
					GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(0.5f, -0.45f, 0.0f);
					break;
				default:
					break;
				}

				GL11.glScalef(0.85f, 0.85f, 0.85f);
			}

			GL11.glRotatef(tileentity.getRotateOffset(), 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.0f, tileentity.getFloatOffset(), 0.0f);
			try{
				int color = tileentity.getCrystalColor();
				GL11.glColor3f(
						((color >> 16) & 0xFF) / 255.0f, //isolate red  & convert to normalized float
						((color >> 8) & 0xFF) / 255.0f, //isolate green & convert to normalized float
						(color & 0xFF) / 255.0f //isolate blue & convert to normalized float
				);
				GlStateManager.pushMatrix();
				GlStateManager.translate(-tileentity.getPos().getX(), -tileentity.getPos().getY(), -tileentity.getPos().getZ());
				Tessellator tesselator = Tessellator.getInstance();
				tesselator.getBuffer().begin(7, DefaultVertexFormats.BLOCK);
				Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), bakedModelCrystal, getWorld().getBlockState(tileentity.getPos()), tileentity.getPos(), tesselator.getBuffer(), false);
				tesselator.draw();
				GlStateManager.popMatrix();
			}catch (Throwable t){
			}
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.disableAlpha();
		RenderHelper.enableStandardItemLighting();
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

}
