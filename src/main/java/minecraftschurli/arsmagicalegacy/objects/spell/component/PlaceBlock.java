package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class PlaceBlock extends SpellComponent {
    private static final String KEY_BLOCKID = "PlaceBlockID";
    private static final String KEY_META = "PlaceMeta";

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                Items.STONE_AXE,
                Items.STONE_PICKAXE,
                Items.STONE_SHOVEL,
                Blocks.CHEST
        };
    }

    private BlockState getPlaceBlock(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_BLOCKID)) {
            return Block.getBlockById(stack.getTagCompound().getInteger(KEY_BLOCKID)).getStateFromMeta(stack.getTagCompound().getInteger(KEY_META));
        }
        return null;
    }

    private void setPlaceBlock(ItemStack stack, BlockState state) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new CompoundNBT());
        stack.getTagCompound().setInteger(KEY_BLOCKID, Block.getIdFromBlock(state.getBlock()));
        stack.getTagCompound().setInteger(KEY_META, state.getBlock().getMetaFromState(state));
        //set lore entry so that the stack displays the name of the block to place
        if (!stack.getTagCompound().hasKey("Lore"))
            stack.getTagCompound().setTag("Lore", new NBTTagList());
        ItemStack blockStack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        NBTTagList tagList = stack.getTagCompound().getTagList("Lore", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); ++i) {
            String str = tagList.getStringTagAt(i);
            if (str.startsWith(String.format(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.placeBlockSpell"), ""))) {
                tagList.removeTag(i);
            }
        }
        tagList.appendTag(new NBTTagString(String.format(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.placeBlockSpell"), blockStack.getDisplayName())));
        stack.getTagCompound().setTag("Lore", tagList);
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!(caster instanceof PlayerEntity))
            return false;
        PlayerEntity player = (PlayerEntity) caster;
        ItemStack spellStack = player.getActiveItemStack();
        if (spellStack == null || spellStack.getItem() != ModItems.spell || !SpellUtils.componentIsPresent(spellStack, PlaceBlock.class))
            return false;
        BlockState bd = getPlaceBlock(spellStack);
        if (bd != null && !caster.isSneaking()) {
            if (world.isAirBlock(pos) || !world.getBlockState(pos).isSideSolid(world, pos, blockFace))
                blockFace = null;
            if (blockFace != null) {
                pos = pos.add(blockFace.getDirectionVec());
            }
            if (world.isAirBlock(pos) || !world.getBlockState(pos).getMaterial().isSolid()) {
                ItemStack searchStack = new ItemStack(bd.getBlock(), 1, bd.getBlock().getMetaFromState(bd));
                if (!world.isRemote && (player.capabilities.isCreativeMode || InventoryUtilities.inventoryHasItem(player.inventory, searchStack, 1))) {
                    world.setBlockState(pos, bd);
                    if (!player.capabilities.isCreativeMode)
                        InventoryUtilities.deductFromInventory(player.inventory, searchStack, 1);
                }
                return true;
            }
        } else if (caster.isSneaking()) {
            if (!world.isRemote && !world.isAirBlock(pos)) {
                setPlaceBlock(spellStack, world.getBlockState(pos));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 5;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.EARTH, Affinity.ENDER);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }
}