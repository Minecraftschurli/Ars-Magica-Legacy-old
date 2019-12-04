package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.power.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.blocks.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.power.*;
import minecraftschurli.arsmagicalegacy.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Light extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(pos).getBlock().equals(ModBlocks.obelisk)) {
            if (RitualShapeHelper.instance.matchesRitual(this, world, pos)) {
                if (!world.isRemote) {
                    RitualShapeHelper.instance.consumeReagents(this, world, pos);
                    RitualShapeHelper.instance.consumeShape(this, world, pos);
                    world.setBlockState(pos, ModBlocks.celestialPrism.getDefaultState());
                    PowerNodeRegistry.For(world).registerPowerNode((IPowerNode<?>) world.getTileEntity(pos));
                } else {
                }
                return true;
            }
        }
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) blockFace = null;
        if (blockFace != null) {
            pos = pos.offset(blockFace);
        }
        if (world.getBlockState(pos).getBlock() != Blocks.AIR)
            return false;
        if (!world.isRemote) {
            world.setBlockState(pos, ModBlocks.blockMageLight.getDefaultState().withProperty(BlockMageLight.COLOR, EnumDyeColor.byMetadata(getColor(stack))));
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.COLOR);
    }

    private int getColor(ItemStack spell) {
        int dye_color_num = 15;
        if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, spell)) {
            ArrayList<SpellModifier> modifiers = SpellUtils.getModifiersForStage(spell, -1);
            for (SpellModifier mod : modifiers) {
                if (mod instanceof Colour) {
                    // not so good
                    dye_color_num = spell.getTagCompound().getCompoundTag("AM2").getCompoundTag(SpellUtils.SPELL_DATA).getCompoundTag(SpellUtils.SPELL_DATA).getInteger("Color");
                }
            }
        }
        return 15 - dye_color_num;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = SpellUtils.getModifiedIntMul(PotionEffectsDefs.default_buff_duration, stack, caster, target, world, SpellModifiers.DURATION);
            //duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new BuffEffectIllumination(duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 50;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 0.5, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2, rand.nextDouble() * 0.2 - 0.1);
                particle.setAffectedByGravity();
                particle.setDontRequireControllers();
                particle.setMaxAge(5);
                particle.setParticleScale(0.1f);
                particle.setRGBColorF(0.6f, 0.2f, 0.8f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NONE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.WHITE_RUNE.get()),
                ModBlocks.cerublossom,
                Blocks.TORCH,
                ModBlocks.vinteumTorch
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        MultiblockStructureDefinition newDef = new MultiblockStructureDefinition("celestialPurification");
        newDef.groups = Lists.newArrayList(RitualShapeHelper.instance.purification.groups);
        MultiblockGroup obelisk = new MultiblockGroup("obelisk", Lists.newArrayList(ModBlocks.obelisk.getDefaultState()), true);
        obelisk.addBlock(new BlockPos(0, 0, 0));
        newDef.addGroup(obelisk);
        return newDef;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_MOONSTONE),
                new ItemStack(ModItems.manaFocus)
        };
    }

    @Override
    public int getReagentSearchRadius() {
        return 3;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getResult() {
        return new ItemStack(ModBlocks.celestialPrism);
    }
}