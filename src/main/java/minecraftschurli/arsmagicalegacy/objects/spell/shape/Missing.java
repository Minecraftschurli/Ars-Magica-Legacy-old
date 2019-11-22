package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Missing extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public boolean isChanneled(){
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipeItems(){
        return null;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack){
        return 1;
    }

    @Override
    public boolean isTerminusShape(){
        return false;
    }

    @Override
    public boolean isPrincipumShape(){
        return false;
    }

//    @Override
//    public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//        return "";
//    }
}