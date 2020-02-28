package minecraftschurli.arsmagicalegacy.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.registration.*;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

import javax.annotation.*;
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
