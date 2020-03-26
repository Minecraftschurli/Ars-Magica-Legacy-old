package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Dig extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.isRemote) return true;
        if (SpellUtils.modifierIsPresent(SpellModifiers.SILKTOUCH_LEVEL, stack) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) <= 0)
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
        else if (SpellUtils.modifierIsPresent(SpellModifiers.FORTUNE_LEVEL, stack) && EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack) <= 0)
            stack.addEnchantment(Enchantments.FORTUNE, SpellUtils.countModifiers(SpellModifiers.FORTUNE_LEVEL, stack));
        BlockState state = world.getBlockState(blockPos);
        float hardness = state.getBlockHardness(world, blockPos);
        if (hardness != -1 && state.getBlock().getHarvestLevel(state) <= SpellUtils.getModifiedIntAdd(2, stack, caster, null, world, SpellModifiers.MINING_POWER)) {
            state.getBlock().harvestBlock(world, (PlayerEntity) caster, blockPos, state, null, stack);
            world.destroyBlock(blockPos, false);
            CapabilityHelper.decreaseMana(caster, hardness * 1.28f);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtils.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.EARTH.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.001F;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 10;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.FORTUNE_LEVEL, SpellModifiers.MINING_POWER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_AXE)),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_PICKAXE)),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_SHOVEL))
        };
    }
}
