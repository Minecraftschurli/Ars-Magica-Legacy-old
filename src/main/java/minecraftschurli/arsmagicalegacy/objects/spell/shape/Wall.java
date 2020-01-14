package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.objects.spell.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import java.util.*;

public class Wall extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 2.5f;
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
//        EntitySpellEffect wall = new EntitySpellEffect(world);
//        wall.setRadius(SpellUtils.getModifiedIntMul(3, stack, caster, target, world, SpellModifiers.RADIUS));
//        wall.setTicksToExist(SpellUtils.getModifiedDoubleAdd(0, stack, caster, target, world, SpellModifiers.GRAVITY));
//        wall.setGravity(SpellUtils.getModifiedIntMul(100, stack, caster, target, world, SpellModifiers.DURATION));
//        wall.SetCasterAndStack(caster, stack);
//        wall.setPosition(x, y, z);
//        wall.setWall(caster.rotationYaw);
//        world.addEntity(wall);
        return SpellCastResult.SUCCESS;
    }

    /**
     * Gets the spell cooldown for this shape
     *
     * @param caster the caster of this spell
     * @return the cooldown in ticks
     */
    @Override
    public int getCooldown(LivingEntity caster) {
        return 4;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get(), 2)),
                new ItemStackSpellIngredient(new ItemStack(Items.COBBLESTONE_WALL)),
                new ItemTagSpellIngredient(Tags.Items.FENCES_WOODEN),
                new EssenceSpellIngredient(EssenceType.ANY, 2500)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }
}
