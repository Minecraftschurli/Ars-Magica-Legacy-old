package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.oredict.*;

import java.util.*;

public class Plant extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block soil = world.getBlockState(pos).getBlock();
        IInventory inventory = DummyPlayerEntity.fromEntityLiving(caster).inventory;
        HashMap<Integer, ItemStack> seeds = GetAllSeedsInInventory(inventory);
        int currentSlot = 0;
        if (soil != Blocks.AIR && seeds.size() > 0) {
            currentSlot = seeds.keySet().iterator().next();
            ItemStack seedStack = seeds.get(currentSlot);
            IPlantable seed = (IPlantable) seedStack.getItem();
            if (soil != null && soil.canSustainPlant(world.getBlockState(pos), world, pos, Direction.UP, seed) && world.isAirBlock(pos.up())) {
                world.setBlockState(pos.up(), seed.getPlant(world, pos));
                seedStack.stackSize--;
                if (seedStack.stackSize <= 0) {
                    inventory.setInventorySlotContents(currentSlot, null);
                    seeds.remove(currentSlot);
                    if (seeds.size() == 0) return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 80;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "plant", x, y + 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.2f, rand.nextDouble() * 0.2 - 0.1);
                particle.setDontRequireControllers();
                particle.setAffectedByGravity();
                particle.setMaxAge(20);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NATURE);
    }

    private HashMap<Integer, ItemStack> GetAllSeedsInInventory(IInventory inventory) {
        HashMap<Integer, ItemStack> seeds = new HashMap<Integer, ItemStack>();
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack == null) continue;
            Item item = slotStack.getItem();
            if (!(item instanceof IPlantable)) continue;
            seeds.put(i, slotStack);
        }
        return seeds;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.GREEN_RUNE.get()),
                Items.WHEAT_SEEDS,
                new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE),
                Items.WHEAT_SEEDS
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}