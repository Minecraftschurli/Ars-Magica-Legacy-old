package minecraftschurli.arsmagicalegacy.init;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class Fluids implements Registries {
    public static final RegistryObject<FlowingFluid> MANA = FLUIDS.register("mana", () -> new ForgeFlowingFluid.Source(Fluids.MANA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MANA_FLOWING = FLUIDS.register("mana_flowing", () -> new ForgeFlowingFluid.Flowing(Fluids.MANA_PROPERTIES));
    public static final RegistryObject<FlowingFluidBlock> MANA_BLOCK = BLOCKS.register("mana_block", () -> new FlowingFluidBlock(Fluids.MANA, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    public static final RegistryObject<Item> MANA_BUCKET = ITEMS.register("mana_bucket", () -> new BucketItem(Fluids.MANA, new Item.Properties().containerItem(net.minecraft.item.Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)));
    public static final ForgeFlowingFluid.Properties MANA_PROPERTIES = new ForgeFlowingFluid.Properties(Fluids.MANA, Fluids.MANA_FLOWING, FluidAttributes.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_still")).color(0x74FFFC).luminosity(3)).bucket(Fluids.MANA_BUCKET).block(MANA_BLOCK);

    public static void register() {}
}
