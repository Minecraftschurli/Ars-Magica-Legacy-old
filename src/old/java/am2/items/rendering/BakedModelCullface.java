package am2.items.rendering;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;

public class BakedModelCullface implements IPerspectiveAwareModel{
	
	IBakedModel parent;
	private ImmutableMap<TransformType, TRSRTransformation> transforms;
	
	public BakedModelCullface(IBakedModel parent, ImmutableMap<TransformType, TRSRTransformation> transforms) {
		this.parent = parent;
		this.transforms = transforms;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		return parent.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return parent.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return parent.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return parent.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return parent.getParticleTexture();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return parent.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		GlStateManager.disableCull();
		return parent.getOverrides();
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
	}

}
