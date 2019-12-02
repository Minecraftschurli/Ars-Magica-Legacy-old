package am2.blocks.render;

import am2.ArsMagica2;
import am2.blocks.BlockIllusionBlock;
import am2.blocks.tileentity.TileEntityIllusionBlock;
import am2.defs.BlockDefs;
import am2.defs.PotionEffectsDefs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TileIllusionBlockRenderer extends TileEntitySpecialRenderer<TileEntityIllusionBlock> {

	@Override
	public void renderTileEntityAt(TileEntityIllusionBlock te, double x, double y, double z, float partialTicks, int destroyStage) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.pushMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		boolean flag = te.getMimicBlock() != null && te.getMimicBlock() != Blocks.AIR.getDefaultState();
		if (BlockIllusionBlock.getIllusionType(te.getWorld().getBlockState(te.getPos())).canBeRevealed())
			flag &= ArsMagica2.proxy.getLocalPlayer() != null && !ArsMagica2.proxy.getLocalPlayer().isPotionActive(PotionEffectsDefs.trueSight);
		if (flag) {
			Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.BLOCK);
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(te.getMimicBlock(), te.getPos(), te.getWorld(), Tessellator.getInstance().getBuffer());
			Tessellator.getInstance().draw();
			if (destroyStage >= 0) {
				Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.BLOCK);
				TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/destroy_stage_" + destroyStage);
	            IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(te.getMimicBlock());
	            IBakedModel ibakedmodel1 = net.minecraftforge.client.ForgeHooksClient.getDamageModel(ibakedmodel, sprite, te.getMimicBlock(), te.getWorld(), te.getPos());
				Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(te.getWorld(), ibakedmodel1, te.getMimicBlock(), te.getPos(), Tessellator.getInstance().getBuffer(), true);
				Tessellator.getInstance().draw();
			}
		} else {
			IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(BlockDefs.illusionBlock.getDefaultState());
			Tessellator t = Tessellator.getInstance();
			VertexBuffer wr = t.getBuffer();
			wr.begin(7, DefaultVertexFormats.BLOCK);
			World world = te.getWorld();
			IBlockState state = world.getBlockState(te.getPos());
			Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(world, model, state, te.getPos(), wr, true);
			t.draw();		}
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}
