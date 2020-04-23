package minecraftschurli.arsmagicalegacy.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        Objects.requireNonNull(registration);
        registration.useNbtForSubtypes(ModItems.INFINITY_ORB.get());
        registration.useNbtForSubtypes(ModItems.INSCRIPTION_UPGRADE.get());
        registration.useNbtForSubtypes(ModItems.AFFINITY_TOME.get());
        registration.registerSubtypeInterpreter(ModItems.POTION_BUNDLE.get(), itemStack -> PotionUtils.getPotionFromItem(itemStack).getRegistryName().toString());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        //registration.addRecipeCategories(new EssenceRefinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //registration.addRecipeTransferHandler(new EssenceRefinerRecipeHandler());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        //registration.addRecipes(RecipesEssenceRefiner.essenceRefinement().getAllRecipes());
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsMagicaAPI.MODID, ArsMagicaAPI.MODID);
    }

}
