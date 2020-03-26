package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public final class Plant extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block soil = world.getBlockState(pos).getBlock();
        IInventory inventory = ((PlayerEntity) caster).inventory;
        HashMap<Integer, ItemStack> seeds = new HashMap<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            Item item = slotStack.getItem();
            if (!(item instanceof IPlantable)) continue;
            seeds.put(i, slotStack);
        }
        int currentSlot;
        if (soil != Blocks.AIR && seeds.size() > 0) {
            currentSlot = seeds.keySet().iterator().next();
            ItemStack seedStack = seeds.get(currentSlot);
            IPlantable seed = (IPlantable) seedStack.getItem();
            if (soil.canSustainPlant(world.getBlockState(pos), world, pos, Direction.UP, seed) && world.isAirBlock(pos.up())) {
                world.setBlockState(pos.up(), seed.getPlant(world, pos));
                seedStack.shrink(1);
                if (seedStack.getCount() <= 0) {
                    inventory.setInventorySlotContents(currentSlot, ItemStack.EMPTY);
                    seeds.remove(currentSlot);
                    if (seeds.size() == 0) return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtils.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.NATURE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREEN_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.WITCHWOOD_SAPLING.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.WHEAT_SEEDS)),
                new ItemStackSpellIngredient(new ItemStack(Items.WHEAT_SEEDS)),
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 15; i++) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "plant", x, y + 1, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.2f, rand.nextDouble() * 0.2 - 0.1);
//                particle.setDontRequireControllers();
//                particle.setAffectedByGravity();
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }
}
