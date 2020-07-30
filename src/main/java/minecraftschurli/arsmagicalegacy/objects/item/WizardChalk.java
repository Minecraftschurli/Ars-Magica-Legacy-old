package minecraftschurli.arsmagicalegacy.objects.item;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2020-05-14
 */
public class WizardChalk extends BlockItem {
    public WizardChalk() {
        super(ModBlocks.WIZARD_CHALK.get(), new Properties().maxDamage(100).group(ArsMagicaLegacy.ITEM_GROUP));
    }

    @Nonnull
    @Override
    public ActionResultType tryPlace(@Nonnull BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if (blockstate == null) {
                    return ActionResultType.FAIL;
                } else if (!this.placeBlock(blockitemusecontext, blockstate)) {
                    return ActionResultType.FAIL;
                } else {
                    BlockPos blockpos = blockitemusecontext.getPos();
                    World world = blockitemusecontext.getWorld();
                    PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (block == blockstate.getBlock()) {
                        this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                        block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if (playerentity instanceof ServerPlayerEntity && !playerentity.isCreative()) {
                            itemstack.attemptDamageItem(1, world.rand, (ServerPlayerEntity) playerentity);
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    if (playerentity != null) {
                        world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, playerentity), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        }
    }
}
