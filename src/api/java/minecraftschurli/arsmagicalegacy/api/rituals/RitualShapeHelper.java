package minecraftschurli.arsmagicalegacy.api.rituals;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class RitualShapeHelper {
	public static boolean matchesRitual(RitualShapeHelper ritualShapeHelper, AbstractRitual ritual, World world, BlockPos pos) {
		int radius = ritual.getReagentSearchRadius();
		List<ItemEntity> items = RitualShapeHelper.getItemsInRange(world, pos, radius);
		if (ritual.getRitualShape().validate(world, pos) == null) {
			return false;
		}
		for (ItemStack stack : ritual.getReagents()) {
			boolean matches = false;
			for (ItemEntity item : items) {
				ItemStack is = item.getItem();
				if (is.getItem().equals(stack.getItem()) && is.getCount() >= stack.getCount()) {
					matches = true;
					break;
				}
			}
			if (!matches) {
				return false;
			}
		}
		return true;
	}

	public static List<ItemStack> checkForRitual(AbstractRitual ritual, World world, BlockPos pos){
		int radius = ritual.getReagentSearchRadius();
		return getItemsInRange(world, pos, radius)
				.stream()
				.sorted(Comparator.comparingInt(value -> value.ticksExisted))
				.map(ItemEntity::getItem)
				.collect(Collectors.toList());
	}
	
	public static void consumeReagents(AbstractRitual ritual, World world, BlockPos pos) {
		List<ItemEntity> items = getItemsInRange(world, pos, ritual.getReagentSearchRadius());
		for (ItemStack stack : ritual.getReagents()) {
			for (ItemEntity item : items) {
				ItemStack is = item.getItem();
				if (is.getItem().equals(stack.getItem()) && is.getCount() >= stack.getCount()) {
					is.shrink(stack.getCount());
					if (is.getCount() <= 0) {
						item.remove();
					} else {
						item.setItem(is);
					}
				}
			}
		}
	}
	
	public static void consumeAllReagents(AbstractRitual ritual, World world, BlockPos pos){
		List<ItemEntity> items = getItemsInRange(world, pos, ritual.getReagentSearchRadius());
		for (ItemEntity item : items) {
			ArsMagicaAPI.LOGGER.debug("Removing Item {}", item.getItem().toString());
			item.remove();
		}
	}
	
	public static void consumeShape(AbstractRitual ritual, World world, BlockPos pos) {
		/*for (MultiblockGroup group : ritual.getRitualShape().getMatchingGroups(world, pos)) {
			for (BlockPos blockPos : group.getPositions()) {
				IBlockState state = world.getBlockState(pos.add(blockPos));
				world.setBlockToAir(pos.add(blockPos));
				world.notifyBlockUpdate(pos.add(blockPos), state, Blocks.AIR.getDefaultState(), 3);
			}
		}*/
	}

	@Nonnull
	private static List<ItemEntity> getItemsInRange(World world, BlockPos pos, int radius) {
		return world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).expand(radius, radius, radius));
	}
}
