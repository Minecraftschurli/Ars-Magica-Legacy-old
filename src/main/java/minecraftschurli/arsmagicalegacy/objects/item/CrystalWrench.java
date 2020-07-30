package minecraftschurli.arsmagicalegacy.objects.item;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class CrystalWrench extends Item {
    public CrystalWrench() {
        super(ModItems.ITEM_1);
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

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (player != null && tileEntity != null && player.isSneaking()) {
            ITextComponent message;
            if (tileEntity instanceof IEtheriumConsumer) {
                ((IEtheriumConsumer) tileEntity).setEtheriumSource(readPos(context.getItem()));
                storePos(context.getItem(), null);
                message = new TranslationTextComponent(ArsMagicaAPI.MODID + ".crystalWrench.linked");
            } else if (tileEntity.getCapability(CapabilityHelper.getEtheriumCapability()).isPresent()) {
                storePos(context.getItem(), pos);
                message = new TranslationTextComponent(ArsMagicaAPI.MODID + ".crystalWrench.saved").appendText("(x=" + pos.getX() + ", y=" + pos.getY() + ", z=" + pos.getZ() + ")");
            } else message = new TranslationTextComponent(ArsMagicaAPI.MODID + ".crystalWrench.failed");
            player.sendStatusMessage(message, true);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
