package minecraftschurli.arsmagicalegacy.api.rituals;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.patchouli.api.IMultiblock;

import javax.annotation.Nonnull;
import java.util.List;

public final class RitualHelper {
	public static boolean matchesRitual(AbstractRitual ritual, World world, BlockPos pos) {
		int radius = ritual.getReagentSearchRadius();
		List<ItemEntity> items = RitualHelper.getItemsInRange(world, pos, radius);
		if (ritual.getRitualShape().validate(world, pos) == null) return false;
		for (ItemStack stack : ritual.getReagents()) {
			boolean matches = false;
			for (ItemEntity item : items) {
				ItemStack is = item.getItem();
				if (is.getItem().equals(stack.getItem()) && is.getCount() >= stack.getCount()) {
					matches = true;
					break;
				}
			}
			if (!matches) return false;
		}
		return true;
	}

	public static void consumeReagents(AbstractRitual ritual, World world, BlockPos pos) {
		List<ItemEntity> items = getItemsInRange(world, pos, ritual.getReagentSearchRadius());
		for (ItemStack stack : ritual.getReagents()) for (ItemEntity item : items) {
		    ItemStack is = item.getItem();
			if (is.getItem().equals(stack.getItem()) && is.getCount() >= stack.getCount()) {
			    is.shrink(stack.getCount());
				if (is.getCount() <= 0) item.remove();
				else item.setItem(is);
			}
		}
	}

	public static void consumeShape(AbstractRitual ritual, World world, BlockPos pos) {
		Rotation rotation = ritual.getRitualShape().validate(world, pos);
		if (rotation == null) return;
		for (IMultiblock.SimulateResult simulateResult : ritual.getRitualShape().simulate(world, pos, rotation, false).getSecond()) {
			world.setBlockState(simulateResult.getWorldPosition(), Blocks.AIR.getDefaultState(), 3);
		}
	}

	@Nonnull
	private static List<ItemEntity> getItemsInRange(World world, BlockPos pos, int radius) {
		return world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(radius,3,radius));
	}

	public static boolean performRitual(AbstractRitual ritual, World world, BlockPos pos) {
		pos = pos.down();
		for (int i = 0; i < 3; i++) {
			BlockPos p = pos.up(i);
			if (RitualHelper.matchesRitual(ritual, world, p)) {
				RitualHelper.consumeReagents(ritual, world, p);
				RitualHelper.consumeShape(ritual, world, p);
				InventoryHelper.spawnItemStack(world, p.getX()+0.5, p.getY()+0.5, p.getZ()+0.5, ritual.getResult());
				return true;
			}
		}
		return false;
	}
}
