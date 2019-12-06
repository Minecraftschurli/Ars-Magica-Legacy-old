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

import java.util.*;

public class Wave extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 3;
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
//        EntitySpellEffect wave = new EntitySpellEffect(world);
//        wave.setRadius((float)SpellUtils.getModifiedDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS));
//        wave.setTicksToExist(SpellUtils.getModifiedIntMul(20, stack, caster, target, world, SpellModifiers.DURATION));
//        wave.SetCasterAndStack(caster, stack);
//        wave.setPosition(x, y + 1, z);
//        wave.setWave(caster.rotationYaw, (float)SpellUtils.getModifiedDoubleAdd(1f, stack, caster, target, world, SpellModifiers.SPEED) * 0.5f);
//        wave.noClip = SpellUtils.modifierIsPresent(SpellModifiers.PIERCING, stack);
//        wave.setGravity(SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack) * 0.5f);
//        world.spawnEntityInWorld(wave);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.MAGIC_WALL.get())),
                new EssenceSpellIngredient(EssenceType.ANY, 2500)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.SPEED, SpellModifiers.PIERCING, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }
}
