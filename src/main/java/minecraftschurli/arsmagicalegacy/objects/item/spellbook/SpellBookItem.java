package minecraftschurli.arsmagicalegacy.objects.item.spellbook;

import minecraftschurli.arsmagicalegacy.init.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class SpellBookItem extends Item {
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> new ItemStackHandler(5) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() == Items.SPELL_ITEM.get();
        }
    });

    public SpellBookItem() {
        super(new Properties().maxStackSize(1).rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(handler.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return handler.cast();
            }
        };
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote){

        }
        return ActionResultType.SUCCESS;
    }
}
