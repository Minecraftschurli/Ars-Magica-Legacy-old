package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class PlaceBlock extends SpellComponent {
    private static final String KEY_BLOCKID = "PlaceBlockID";

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(Items.STONE_AXE)),
                new ItemStackSpellIngredient(new ItemStack(Items.STONE_PICKAXE)),
                new ItemStackSpellIngredient(new ItemStack(Items.STONE_SHOVEL)),
                new ItemStackSpellIngredient(new ItemStack(Items.CHEST))
        };
    }

    private BlockState getPlaceBlock(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().get(KEY_BLOCKID) != null)
            return Block.getStateById(stack.getTag().getInt(KEY_BLOCKID));
        return null;
    }

    private void setPlaceBlock(ItemStack stack, BlockState state) {
        if (!stack.hasTag()) stack.setTag(new CompoundNBT());
        stack.getTag().putInt(KEY_BLOCKID, Block.getStateId(state));
        if (stack.getTag().get("Lore") == null) stack.getTag().put("Lore", new ListNBT());
        ItemStack blockStack = new ItemStack(state.getBlock());
        ListNBT tagList = stack.getTag().getList("Lore", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); ++i) {
            String str = tagList.getString(i);
            if (str.startsWith(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.placeBlockSpell").toString()))
                tagList.remove(i);
        }
        tagList.add(StringNBT.valueOf(String.format(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.placeBlockSpell").toString(), blockStack.getDisplayName().toString())));
        stack.getTag().put("Lore", tagList);
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!(caster instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) caster;
        ItemStack spellStack = player.getActiveItemStack();
        if (spellStack.getItem() != ModItems.SPELL.get() || !SpellUtils.componentIsPresent(spellStack, PlaceBlock.class))
            return false;
        BlockState bd = getPlaceBlock(spellStack);
        if (bd != null && !caster.func_226296_dJ_()) {
            if (world.isAirBlock(pos) || !world.getBlockState(pos).isSolid()) blockFace = null;
            if (blockFace != null) pos = pos.add(blockFace.getDirectionVec());
            if (world.isAirBlock(pos) || !world.getBlockState(pos).getMaterial().isSolid()) {
                ItemStack searchStack = new ItemStack(bd.getBlock());
                if (!world.isRemote && (player.isCreative() || player.inventory.hasItemStack(searchStack))) {
                    world.setBlockState(pos, bd);
                    if (!player.isCreative()) player.inventory.deleteStack(searchStack);
                }
                return true;
            }
        } else if (caster.func_226296_dJ_()) {
            if (!world.isRemote && !world.isAirBlock(pos)) setPlaceBlock(spellStack, world.getBlockState(pos));
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
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.EARTH.get(), ModSpellParts.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.05f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
