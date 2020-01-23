package minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable;

import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModContainers;
import minecraftschurli.arsmagicalegacy.objects.spell.SpellValidator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WritableBookItem;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.EnumSet;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class InscriptionTableContainer extends Container {
    private static final int PLAYER_INVENTORY_START = 1;
    private static final int PLAYER_ACTION_BAR_START = 28;
    private static final int PLAYER_ACTION_BAR_END = 37;
    private final PlayerInventory inventoryPlayer;
    private InscriptionTableTileEntity table;

    public InscriptionTableContainer(int id, PlayerInventory inventory, InscriptionTableTileEntity table) {
        super(ModContainers.INSCRIPTION_TABLE.get(), id);
        this.table = table;
        this.inventoryPlayer = inventory;
        addSlot(new InscriptionTableSlot(table, 0, 102, 74));

        //display player inventory
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(inventory, k + i * 9 + 9, 30 + k * 18, 170 + i * 18));
            }
        }

        //display player action bar
        for (int j1 = 0; j1 < 9; j1++) {
            addSlot(new Slot(inventory, j1, 30 + j1 * 18, 228));
        }
    }

    public InscriptionTableContainer(int id, PlayerInventory inventoryplayer, PacketBuffer additionalData) {
        this(id, inventoryplayer, (InscriptionTableTileEntity) inventoryplayer.player.world.getTileEntity(additionalData.readBlockPos()));
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity entityplayer) {
        return table.isUsableByPlayer(entityplayer);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity par1PlayerEntity, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < PLAYER_INVENTORY_START) {
                if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (i < PLAYER_ACTION_BAR_START) //from player inventory
            {
                if (!mergeSpecialItems(itemstack1, slot)) {
                    if (!mergeItemStack(itemstack1, PLAYER_ACTION_BAR_START, PLAYER_ACTION_BAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (i < PLAYER_ACTION_BAR_END) {
                if (!mergeSpecialItems(itemstack1, slot)) {
                    if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_START - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, PLAYER_INVENTORY_START, PLAYER_ACTION_BAR_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() != itemstack.getCount()) {
                slot.onSlotChange(itemstack1, itemstack);
            } else {
                return ItemStack.EMPTY;
            }
        }
        return itemstack;
    }

    private boolean mergeSpecialItems(ItemStack stack, Slot slot) {
        if (stack.getItem() instanceof WritableBookItem) {
            Slot bookSlot = inventorySlots.get(0);
            if (bookSlot.getHasStack()) return false;

            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            bookSlot.putStack(newStack);
            bookSlot.onSlotChanged();

            stack.setCount(stack.getCount() - 1);
            if (stack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
                slot.onSlotChanged();
            }
            return true;
        }
        return false;
    }

    public int getCurrentRecipeSize() {
        return table.getCurrentRecipe().size();
    }

    public boolean currentRecipeContains(AbstractSpellPart part) {
        return table.getCurrentRecipe().contains(part);
    }

    public AbstractSpellPart getRecipeItemAt(int index) {
        return table.getCurrentRecipe().get(index);
    }

    public void removeMultipleRecipeParts(int startIndex, int length) {
        table.removeMultipleSpellParts(startIndex, length);
    }

    public void removeSingleRecipePart(int index) {
        table.removeSpellPart(index);
    }

    public void addRecipePart(AbstractSpellPart part) {
        table.addSpellPart(part);
    }

    public void addRecipePartToGroup(int groupIndex, AbstractSpellPart part) {
        table.addSpellPartToStageGroup(groupIndex, part);
    }

    public void removeSingleRecipePartFromGroup(int groupIndex, int index) {
        table.removeSpellPartFromStageGroup(index, groupIndex);
    }

    public void removeMultipleRecipePartsFromGroup(int groupIndex, int startIndex, int length) {
        table.removeMultipleSpellPartsFromStageGroup(startIndex, length, groupIndex);
    }

    public int getNumStageGroups() {
        return table.getNumStageGroups();
    }

    public int getShapeGroupSize(int groupIndex) {
        return table.getShapeGroupSize(groupIndex);
    }

    public AbstractSpellPart getShapeGroupPartAt(int groupIndex, int index) {
        return table.getShapeGroupPartAt(groupIndex, index);
    }

    public String getSpellName() {
        return table.getSpellName();
    }

    public void setSpellName(String name) {
        table.setSpellName(name);
    }

    public void giveSpellToPlayer(PlayerEntity player) {
        table.createSpellForPlayer(player);
    }

    public boolean slotHasStack(int slot) {
        return this.inventorySlots.get(slot).getHasStack();
    }

    public boolean slotIsBook(int slot) {
        return slotHasStack(slot) &&
                this.inventorySlots.get(slot).getStack().getItem() == Items.WRITTEN_BOOK &&
                this.inventorySlots.get(slot).getStack().getTag() != null &&
                !this.inventorySlots.get(slot).getStack().getTag().getBoolean("spellFinalized");
    }

    public SpellValidator.ValidationResult validateCurrentDefinition() {
        return table.currentRecipeIsValid();
    }

    public boolean modifierCanBeAdded(SpellModifier modifier) {
        EnumSet<SpellModifiers> modifiers = modifier.getAspectsModified();
        for (SpellModifiers mod : modifiers) {
            if (table.getModifierCount(mod) > 2)
                return false;
        }
        return true;
    }

    public boolean currentSpellDefIsReadOnly() {
        return table.currentSpellDefIsReadOnly();
    }

    public void resetSpellNameAndIcon() {
        ItemStack stack = this.inventorySlots.get(0).getStack();
        if (!stack.isEmpty()) {
            table.resetSpellNameAndIcon(stack, inventoryPlayer.player);
        }
        this.inventorySlots.get(0).onSlotChanged();
        detectAndSendChanges();
    }
}
