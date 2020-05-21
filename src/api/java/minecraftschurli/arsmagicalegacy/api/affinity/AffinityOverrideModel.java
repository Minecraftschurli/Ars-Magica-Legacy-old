package minecraftschurli.arsmagicalegacy.api.affinity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BakedModelWrapper;

/**
 * @author Minecraftschurli
 * @version 2020-05-17
 */
public class AffinityOverrideModel extends BakedModelWrapper<IBakedModel> {
    private final ItemOverrideList itemHandler = new ItemOverrideList() {
        @Override
        public IBakedModel getModelWithOverrides(@Nonnull IBakedModel original, @Nonnull ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
            Affinity affinity = IAffinityItem.getAffinity(stack);
            if (affinity == null) return original;
            ResourceLocation rl = affinity.getTextureLocation(AffinityOverrideModel.this.suffix);
            if (rl == null) return original;
            ModelResourceLocation modelPath = new ModelResourceLocation(rl, "inventory");
            return Minecraft.getInstance().getModelManager().getModel(modelPath);
        }
    };
    private final String suffix;

    public AffinityOverrideModel(IBakedModel originalModel, String suffix) {
        super(originalModel);
        this.suffix = suffix;
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return itemHandler;
    }
}
