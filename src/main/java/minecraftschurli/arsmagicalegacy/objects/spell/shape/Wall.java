package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.objects.entity.WallEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public final class Wall extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (world.isRemote) return SpellCastResult.SUCCESS;
        WallEntity wall = new WallEntity(world);
        wall.setRadius((float) SpellUtil.modifyDoubleAdd(3, stack, caster, target, world, SpellModifiers.RADIUS));
        wall.setTicksToExist(SpellUtil.modifyIntAdd(0, stack, caster, target, world, SpellModifiers.GRAVITY));
        wall.setGravity(SpellUtil.modifyIntMul(100, stack, caster, target, world, SpellModifiers.DURATION));
        wall.setCasterAndStack(caster, stack);
        wall.setPosition(x, y, z);
        world.addEntity(wall);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.FENCES_WOODEN),
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(Items.COBBLESTONE_WALL)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get(), 2)),
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
