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

import java.awt.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class Fluids implements Registries {
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE = FLUIDS.register(
            "liquid_essence",
            () -> new ForgeFlowingFluid.Source(Fluids.LIQUID_ESSENCE_PROPERTIES)
    );
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE_FLOWING = FLUIDS.register(
            "liquid_essence_flowing",
            () -> new ForgeFlowingFluid.Flowing(Fluids.LIQUID_ESSENCE_PROPERTIES)
    );
    public static final RegistryObject<FlowingFluidBlock> LIQUID_ESSENCE_BLOCK = BLOCKS.register(
            "liquid_essence_block",
            () -> new FlowingFluidBlock(
                    Fluids.LIQUID_ESSENCE,
                    Block.Properties.create(Material.WATER)
                    .doesNotBlockMovement()
                    .hardnessAndResistance(100.0F)
                    .noDrops()
            )
    );
    public static final RegistryObject<Item> LIQUID_ESSENCE_BUCKET = ITEMS.register(
            "liquid_essence_bucket",
            () -> new BucketItem(
                    Fluids.LIQUID_ESSENCE,
                    new Item.Properties()
                            .containerItem(net.minecraft.item.Items.BUCKET)
                            .maxStackSize(1).group(ItemGroup.MISC)
            )
    );
    public static final ForgeFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(
            Fluids.LIQUID_ESSENCE,
            Fluids.LIQUID_ESSENCE_FLOWING,
            FluidAttributes.builder(
                    new ResourceLocation("minecraft","block/water_still"),
                    new ResourceLocation("minecraft","block/water_flow")
            ).color(new Color(0x74FFFC).getRGB()))
            .bucket(Fluids.LIQUID_ESSENCE_BUCKET)
            .block(LIQUID_ESSENCE_BLOCK);

    public static void register() {}
}
