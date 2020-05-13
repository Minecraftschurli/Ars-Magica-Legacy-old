package minecraftschurli.arsmagicalegacy.objects.item.affinitytome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
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
 * @version 2020-04-22
 */
public class AffinityTomeModel extends BakedModelWrapper<IBakedModel> {
    private final ItemOverrideList itemHandler = new ItemOverrideList() {
        @Override
        public IBakedModel getModelWithOverrides(@Nonnull IBakedModel original, @Nonnull ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
            Affinity affinity = AffinityTomeItem.getAffinity(stack);
            if (affinity == null) return original;
            ResourceLocation rl = affinity.getTextureLocation();
            if (rl == null) return original;
            ModelResourceLocation modelPath = new ModelResourceLocation(rl, "inventory");
            return Minecraft.getInstance().getModelManager().getModel(modelPath);
        }
    };

    public AffinityTomeModel(IBakedModel originalModel) {
        super(originalModel);
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return itemHandler;
    }
}
