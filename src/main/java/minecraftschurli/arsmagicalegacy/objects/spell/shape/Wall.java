package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class Wall extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
//        if (world.isRemote) return SpellCastResult.SUCCESS;
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
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get(), 2)),
                new ItemStackSpellIngredient(new ItemStack(Items.COBBLESTONE_WALL)),
                new ItemTagSpellIngredient(Tags.Items.FENCES_WOODEN),
                new EtheriumSpellIngredient(2500)
        };
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 2.5f;
    }
}
