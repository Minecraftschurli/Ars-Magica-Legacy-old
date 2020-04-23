package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class CrystalWrenchItem extends Item {
    public CrystalWrenchItem() {
        super(ModItems.ITEM_1);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (player == null || tileEntity == null)
            return ActionResultType.PASS;
        if (player.isShiftKeyDown()) {
            if (tileEntity instanceof IEtheriumConsumer) {
                ((IEtheriumConsumer) tileEntity).setEtheriumSource(readPos(context.getItem()));
                storePos(context.getItem(), null);
                return ActionResultType.SUCCESS;
            } else if (tileEntity.getCapability(CapabilityHelper.getEtheriumCapability()).isPresent()) {
                storePos(context.getItem(), pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    public static void storePos(ItemStack stack, BlockPos pos) {
        if (pos == null) {
            stack.getOrCreateTag().remove("storedPos");
            return;
        }
        CompoundNBT posNBT = new CompoundNBT();
        posNBT.putInt("x", pos.getX());
        posNBT.putInt("y", pos.getY());
        posNBT.putInt("z", pos.getZ());
        stack.getOrCreateTag().put("storedPos", posNBT);
    }

    public static BlockPos readPos(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        if (tag == null || !tag.contains("storedPos")) return null;
        CompoundNBT storedPos = tag.getCompound("storedPos");
        return new BlockPos(storedPos.getInt("x"), storedPos.getInt("y"), storedPos.getInt("z"));
    }
}
