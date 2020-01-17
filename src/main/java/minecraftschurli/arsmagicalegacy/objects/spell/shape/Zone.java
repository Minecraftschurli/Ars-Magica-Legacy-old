package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import java.util.*;

public class Zone extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 4.5f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (world.isRemote) return SpellCastResult.SUCCESS;
//        EntitySpellEffect zone = new EntitySpellEffect(world);
//        zone.setRadius(SpellUtils.getModifiedIntAdd(2, stack, caster, target, world, SpellModifiers.RADIUS));
//        zone.setTicksToExist(SpellUtils.getModifiedIntMul(100, stack, caster, target, world, SpellModifiers.DURATION));
//        zone.setGravity(SpellUtils.getModifiedDoubleAdd(0, stack, caster, target, world, SpellModifiers.GRAVITY));
//        zone.SetCasterAndStack(caster, stack);
//        zone.setPosition(x, y, z);
//        world.spawnEntityInWorld(zone);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_MOONSTONE),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TARMA_ROOT.get())),
                new ItemTagSpellIngredient(Tags.Items.GEMS_DIAMOND)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return null;
    }
}
