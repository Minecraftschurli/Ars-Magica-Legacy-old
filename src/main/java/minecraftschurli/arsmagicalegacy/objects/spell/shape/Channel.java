package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.spell.EssenceType;
import minecraftschurli.arsmagicalegacy.util.SpellHelper;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Channel extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount) {
        boolean shouldApplyEffect = useCount % 10 == 0/* || SpellUtils.componentIsPresent(stack, Telekinesis.class, 0) || SpellUtils.componentIsPresent(stack, Attract.class, 0) || SpellUtils.componentIsPresent(stack, Repel.class, 0)*/;
        if (shouldApplyEffect){
            SpellCastResult result = SpellHelper.applyStageToEntity(stack, caster, world, caster, 0, giveXP);
            if (result != SpellCastResult.SUCCESS) return result;
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack) {
        return 1;
    }

    @Override
    public ISpellIngredient[] getRecipeItems(){
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ESSENCE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TARMA_ROOT.get())),
                new EssenceSpellIngredient(EssenceType.ANY, 500)
        };
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

//    @Override
//    public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//        switch (affinity){
//            case AIR:
//                return "arsmagica2:spell.loop.air";
//            case ARCANE:
//                return "arsmagica2:spell.loop.arcane";
//            case EARTH:
//                return "arsmagica2:spell.loop.earth";
//            case ENDER:
//                return "arsmagica2:spell.loop.ender";
//            case FIRE:
//                return "arsmagica2:spell.loop.fire";
//            case ICE:
//                return "arsmagica2:spell.loop.ice";
//            case LIFE:
//                return "arsmagica2:spell.loop.life";
//            case LIGHTNING:
//                return "arsmagica2:spell.loop.lightning";
//            case NATURE:
//                return "arsmagica2:spell.loop.nature";
//            case WATER:
//                return "arsmagica2:spell.loop.water";
//            case NONE:
//            default:
//                return "arsmagica2:spell.loop.none";
//        }
//    }
}
