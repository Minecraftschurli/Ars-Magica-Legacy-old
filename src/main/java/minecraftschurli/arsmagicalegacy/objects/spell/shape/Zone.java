package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.objects.entity.ZoneEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public final class Zone extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (world.isRemote) return SpellCastResult.SUCCESS;
        ZoneEntity zone = new ZoneEntity(world);
        zone.setRadius(SpellUtil.modifyIntAdd(2, stack, caster, target, world, SpellModifiers.RADIUS));
        zone.setTicksToExist(SpellUtil.modifyIntMul(100, stack, caster, target, world, SpellModifiers.DURATION));
        zone.setGravity(SpellUtil.modifyDoubleAdd(0, stack, caster, target, world, SpellModifiers.GRAVITY));
        zone.setCasterAndStack(caster, stack);
        zone.setPosition(x, y, z);
        world.addEntity(zone);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.GEMS_DIAMOND),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_MOONSTONE),
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TARMA_ROOT.get()))
        };
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
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
        return 4.5f;
    }
}
