package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.extensions.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.oredict.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class Recall extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) {
            return false;
        }
        if (caster.isPotionActive(PotionEffectsDefs.astralDistortion) || ((LivingEntity) target).isPotionActive(PotionEffectsDefs.astralDistortion)) {
            if (caster instanceof PlayerEntity)
                ((PlayerEntity) caster).addChatMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.cantTeleport")));
            return false;
        }
        if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
            ItemStack[] ritualRunes = RitualShapeHelper.instance.checkForRitual(this, world, target.getPosition());
            if (ritualRunes != null) {
                return handleRitualReagents(ritualRunes, world, target.getPosition(), caster, target);
            }
        }
        IEntityExtension casterProperties = EntityExtension.For(caster);
        if (casterProperties.getMarkDimensionID() == -512) {
            if (caster instanceof PlayerEntity && !world.isRemote)
                ((PlayerEntity) caster).addChatMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.noMark")));
            return false;
        } else if (casterProperties.getMarkDimensionID() != caster.dimension) {
            if (caster instanceof PlayerEntity && !world.isRemote)
                ((PlayerEntity) caster).addChatMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.diffDimMark")));
            return false;
        }
        if (!world.isRemote) {
            ((LivingEntity) target).setPositionAndUpdate(casterProperties.getMarkX(), casterProperties.getMarkY(), casterProperties.getMarkZ());
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    private boolean handleRitualReagents(ItemStack[] ritualRunes, World world, BlockPos pos, LivingEntity caster, Entity target) {
        boolean hasVinteumDust = false;
        for (ItemStack stack : ritualRunes) {
            if (stack.getItem() == ModItems.itemOre && stack.getItemDamage() == ItemOre.META_VINTEUM) {
                hasVinteumDust = true;
                break;
            }
        }
        if (!hasVinteumDust && ritualRunes.length == 3) {
            long key = KeystoneUtilities.instance.getKeyFromRunes(ritualRunes);
            Vec3d vector = ArsMagica2.proxy.blocks.getNextKeystonePortalLocation(world, pos, false, key);
            if (vector == null || vector.equals(new Vec3d(pos))) {
                if (caster instanceof PlayerEntity && !world.isRemote)
                    ((PlayerEntity) caster).addChatMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.noMatchingGate")));
                return false;
            } else {
                RitualShapeHelper.instance.consumeAllReagents(this, world, pos);
                RitualShapeHelper.instance.consumeShape(this, world, pos);
                ((LivingEntity) target).setPositionAndUpdate(vector.x, vector.y - target.height, vector.z);
                return true;
            }
        } else if (hasVinteumDust) {
            ArrayList<Integer> copy = new ArrayList<Integer>();
            for (ItemStack stack : ritualRunes) {
                if (stack.getItem() == ModItems.rune && stack.getItemDamage() <= 16) {
                    copy.add(stack.getItemDamage());
                }
            }
            int[] newRunes = new int[copy.size()];
            for (int i = 0; i < copy.size(); i++) {
                newRunes[i] = copy.get(i);
            }
            PlayerEntity player = SelectionUtils.getPlayersForRuneSet(newRunes);
            if (player == null) {
                if (caster instanceof PlayerEntity && !world.isRemote)
                    ((PlayerEntity) caster).addChatMessage(new TextComponentString("minecraftschurli.arsmagicalegacy.tooltip.noMatchingPlayer"));
                return false;
            } else if (player == caster) {
                if (caster instanceof PlayerEntity && !world.isRemote)
                    ((PlayerEntity) caster).addChatMessage(new TextComponentString("minecraftschurli.arsmagicalegacy.tooltip.cantSummonSelf"));
                return false;
            } else {
                RitualShapeHelper.instance.consumeAllReagents(this, world, pos);
                if (target.worldObj.provider.getDimension() != caster.worldObj.provider.getDimension()) {
                    DimensionUtilities.doDimensionTransfer(player, caster.worldObj.provider.getDimension());
                }
                target.setPositionAndUpdate(pos.getX(), pos.getY() + 0.5D, pos.getZ());
                return true;
            }
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 500;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "arcane", x, y - 1, z);
            if (particle != null) {
                particle.addRandomOffset(1, 0, 1);
                particle.AddParticleController(new ParticleExpandingCollapsingRingAtPoint(particle, x, y - 1, z, 0.1, 3, 0.3, 1, false).setCollapseOnce());
                particle.setMaxAge(20);
                particle.setParticleScale(0.2f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ARCANE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.ORANGE_RUNE.get()),
                Items.COMPASS,
                new ItemStack(Items.MAP),
                Items.ENDER_PEARL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.1f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.ringedCross;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(ModItems.rune, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.rune, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModItems.rune, 1, OreDictionary.WILDCARD_VALUE)
        };
    }

    @Override
    public int getReagentSearchRadius() {
        return RitualShapeHelper.instance.ringedCross.getWidth();
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getResult() {
        return new ItemStack(ModBlocks.keystoneRecepticle);
    }
}