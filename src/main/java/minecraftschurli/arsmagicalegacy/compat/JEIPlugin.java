package minecraftschurli.arsmagicalegacy.compat;

import mezz.jei.api.*;
import mezz.jei.api.registration.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.util.*;

import javax.annotation.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(ModItems.INFINITY_ORB.get());
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
        return new ResourceLocation(ArsMagicaLegacy.MODID, ArsMagicaLegacy.MODID);
    }
}
