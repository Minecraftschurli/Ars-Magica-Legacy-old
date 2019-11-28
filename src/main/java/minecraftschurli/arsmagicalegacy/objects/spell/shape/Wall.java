package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.spell.EssenceType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

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

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.COBBLESTONE_WALL)),
                new ItemStackSpellIngredient(new ItemStack(Items.OAK_FENCE)),
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
