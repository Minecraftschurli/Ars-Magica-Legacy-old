package minecraftschurli.arsmagicalegacy.objects.spell.component;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.util.EntityUtils;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Forge extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        applyFurnaceToBlockAtCoords(caster, world, pos);
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof VillagerEntity/* && ArsMagicaLegacy.config.forgeSmeltsVillagers()*/) {
            if (!world.isRemote && !EntityUtils.isSummon((LivingEntity) target))
                target.entityDropItem(new ItemStack(Items.EMERALD, 1));
            if (caster instanceof PlayerEntity)
                target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) caster), 5000);
            else target.attackEntityFrom(DamageSource.causeMobDamage(caster), 5000);
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 55;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "radiant", x + 0.5, y + 0.5, z + 0.5);
//        if (particle != null) {
//            particle.AddParticleController(new ParticleHoldPosition(particle, 20, 1, false));
//            particle.setMaxAge(20);
//            particle.setParticleScale(0.3f);
//            particle.setRGBColorF(0.7f, 0.4f, 0.2f);
//            particle.SetParticleAlpha(0.1f);
//            if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.FIRE);
//    }
//
    @SuppressWarnings("deprecated")
    private boolean applyFurnaceToBlockAtCoords(LivingEntity entity, World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block.equals(Blocks.AIR)) return false;
        if (block.equals(Blocks.ICE)) {
            if (!world.isRemote) world.setBlockState(pos, Blocks.WATER.getDefaultState());
            return true;
        }
        Optional<FurnaceRecipe> recipe = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(new ItemStack(block)), world);
        if (!recipe.isPresent()) return false;
        ItemStack smelted = recipe.get().getRecipeOutput();
        if (!world.isRemote) {
            if (smelted.getItem() instanceof BlockItem)
                world.setBlockState(pos, ((BlockItem) smelted.getItem()).getBlock().getDefaultState());
            else {
                ItemEntity item = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, smelted.copy());
                float f3 = 0.05F;
                item.setMotion(world.rand.nextGaussian() * f3, world.rand.nextGaussian() * f3 + 0.2F, world.rand.nextGaussian() * f3);
                world.addEntity(item);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        return true;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.FURNACE))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
