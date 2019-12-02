package am2.blocks.render;

import org.lwjgl.opengl.GL11;

import am2.blocks.BlockCrystalMarker;
import am2.blocks.tileentity.TileEntityCrystalMarker;
import am2.defs.BlockDefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;

public class TileCrystalMarkerRenderer extends TileEntitySpecialRenderer<TileEntityCrystalMarker>{
	private IModel model;
	private IBakedModel bakedModel;
	
	private IBakedModel getBakedModel() {
		try {
			model = ModelLoaderRegistry.getModel(new ResourceLocation("arsmagica2", "block/crystal_marker.obj"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
		return bakedModel;
	}

	
	public TileCrystalMarkerRenderer(){
	}

	public void renderTileEntityAt(TileEntityCrystalMarker tileentity, double x, double y, double z, float partialTicks, int destroyStage){
		EnumFacing facing = EnumFacing.UP;
		
		if (tileentity.getWorld() != null){
			facing = tileentity.getFacing();
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GL11.glDisable(GL11.GL_CULL_FACE);
		RenderHelper.disableStandardItemLighting();

		if (tileentity.getPos() != BlockPos.ORIGIN){
			switch (facing){
			case UP: //Bottom, Inventory is above
				GL11.glTranslated(x + 0.5, y + 1.0 + tileentity.GetConnectedBoundingBox().minY, z + 0.5);
				GL11.glRotated(90, 1, 0, 0);
				break;
			case DOWN: //Top, Inventory is below
				GL11.glTranslated(x + 0.5, y - (1.0 - tileentity.GetConnectedBoundingBox().maxY), z + 0.5);
				GL11.glRotated(270, 1, 0, 0);
				break;
			case SOUTH: //North, Inventory is to the south
				GL11.glTranslated(x + 0.5, y + 0.5, z + 1.0 + (1.0 - tileentity.GetConnectedBoundingBox().maxZ));
				GL11.glRotated(180, 0, 1, 0);
				break;
			case NORTH: //South, Inventory is to the north
				GL11.glTranslated(x + 0.5, y + 0.5, z - tileentity.GetConnectedBoundingBox().minZ);
				break;
			case EAST: //West, Inventory is to the east
				GL11.glTranslated(x + 1 + tileentity.GetConnectedBoundingBox().minX, y + 0.5, z + 0.5);
				GL11.glRotated(270, 0, 1, 0);
				break;
			case WEST: //East, Inventory is to the west
				GL11.glTranslated(x - (1.0 - tileentity.GetConnectedBoundingBox().maxX), y + 0.5, z + 0.5);
				GL11.glRotated(90, 0, 1, 0);
				break;
			}

			GL11.glScalef(0.5f, 0.5f, 0.5f);
		}else{
			GL11.glTranslated(0.5, 0.5, 0.5);
			GL11.glScalef(1.4f, 1.4f, 1.4f);
			GL11.glRotated(180, 0, 1, 0);
		}

		int blockType = 0;

		if (tileentity.getWorld() != null && destroyStage != -10){
			blockType = tileentity.getWorld().getBlockState(tileentity.getPos()).getValue(BlockCrystalMarker.TYPE);
		}else{
			blockType = (int)partialTicks;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(-tileentity.getPos().getX(), -tileentity.getPos().getY(), -tileentity.getPos().getZ());
		Tessellator tesselator = Tessellator.getInstance();
		tesselator.getBuffer().begin(7, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(getWorld(), getBakedModel(), BlockDefs.crystalMarker.getDefaultState().withProperty(BlockCrystalMarker.TYPE, blockType), tileentity.getPos(), tesselator.getBuffer(), false);
		tesselator.draw();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glPopAttrib();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.enableBlend();
		GL11.glPopMatrix();
	}

}
