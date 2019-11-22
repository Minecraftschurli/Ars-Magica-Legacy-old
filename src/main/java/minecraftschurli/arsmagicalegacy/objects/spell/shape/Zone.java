package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Zone extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount){
        if (world.isRemote) return SpellCastResult.SUCCESS;
//        int radius = SpellUtils.getModifiedIntAdd(2, stack, caster, target, world, 0, SpellModifiers.RADIUS);
//        double gravity = SpellUtils.getModifiedDoubleAdd(0, stack, caster, target, world, 0, SpellModifiers.GRAVITY);
//        int duration = SpellUtils.getModifiedIntMul(100, stack, caster, target, world, 0, SpellModifiers.DURATION);
//        EntitySpellEffect zone = new EntitySpellEffect(world);
//        zone.setRadius(radius);
//        zone.setTicksToExist(duration);
//        zone.setGravity(gravity);
//        zone.SetCasterAndStack(caster, SpellUtils.instance.popStackStage(stack));
//        zone.setPosition(x, y, z);
//        world.spawnEntityInWorld(zone);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean isChanneled(){
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipeItems(){
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.TARMA_ROOT.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MOONSTONE_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.SUNSTONE_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.DIAMOND))
        };
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack){
        return 4.5f;
    }

    @Override
    public boolean isTerminusShape(){
        return false;
    }

    @Override
    public boolean isPrincipumShape(){
        return true;
    }
//
//    @Override
//    public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//        switch (affinity){
//            case AIR:
//                return "arsmagica2:spell.cast.air";
//            case ARCANE:
//                return "arsmagica2:spell.cast.arcane";
//            case EARTH:
//                return "arsmagica2:spell.cast.earth";
//            case ENDER:
//                return "arsmagica2:spell.cast.ender";
//            case FIRE:
//                return "arsmagica2:spell.cast.fire";
//            case ICE:
//                return "arsmagica2:spell.cast.ice";
//            case LIFE:
//                return "arsmagica2:spell.cast.life";
//            case LIGHTNING:
//                return "arsmagica2:spell.cast.lightning";
//            case NATURE:
//                return "arsmagica2:spell.cast.nature";
//            case WATER:
//                return "arsmagica2:spell.cast.water";
//            case NONE:
//            default:
//                return "arsmagica2:spell.cast.none";
//        }
//    }
}
