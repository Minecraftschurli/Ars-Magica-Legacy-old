package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.UUID;

public class Toggle extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 0.7f;
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
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        String current = stack.getTag().getString("ToggleShapeID");
//        ArrayList<ItemStack> rs = EntityExtension.For(caster).runningStacks;
        int foundID = -1;
//        for (int i = 0; i < rs.size(); i++) {
//            ItemStack is = rs.get(i);
//            if (is != null && is.getTag() != null && is.getTag().getString("ToggleShapeID").equals(current)) {
//                foundID = i;
//                break;
//            }
//        }
        if (foundID != -1) {
//            EntityExtension.For(caster).runningStacks.remove(foundID);
            if (caster instanceof PlayerEntity) {
                PlayerInventory inv = ((PlayerEntity) caster).inventory;
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack is = inv.getStackInSlot(i);
                    if (is != null && is.getItem() instanceof SpellItem && is.getTag() != null && is.getTag().getString("ToggleShapeID").equals(current))
                        is.getTag().putBoolean("HasEffect", false);
                }
            }
        } else {
//            EntityExtension.For(caster).runningStacks.add(stack.copy());
            if (caster instanceof PlayerEntity) {
                PlayerInventory inv = ((PlayerEntity) caster).inventory;
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    ItemStack is = inv.getStackInSlot(i);
                    if (is != null && is.getItem() instanceof SpellItem && is.getTag() != null && is.getTag().getString("ToggleShapeID").equals(current))
                        is.getTag().putBoolean("HasEffect", true);
                }
            }
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURIFIED_VINTEUM.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.LEVER)),
                new ItemStackSpellIngredient(new ItemStack(ModItems.GREATER_FOCUS.get())),
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
        tag.putString("ToggleShapeID", UUID.randomUUID().toString());
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }
}
