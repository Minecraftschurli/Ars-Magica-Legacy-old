package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.fluid.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.*;

import java.awt.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModFluids implements IInit {
    public static final Item.Properties BUCKET_PROPERTIES = new Item.Properties()
            .containerItem(net.minecraft.item.Items.BUCKET)
            .maxStackSize(1).group(ItemGroup.MISC);

    public static final Block.Properties FLUID_BLOCK_PROPERTIES = Block.Properties.create(Material.WATER)
            .doesNotBlockMovement()
            .hardnessAndResistance(100.0F)
            .noDrops();
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE = FLUIDS.register(
            "liquid_essence",
            LiquidEssenceFluid.Source::new
    );
    public static final RegistryObject<FlowingFluidBlock> LIQUID_ESSENCE_BLOCK = BLOCKS.register(
            "liquid_essence_block",
            () -> new FlowingFluidBlock(ModFluids.LIQUID_ESSENCE, FLUID_BLOCK_PROPERTIES)
    );
    public static final RegistryObject<Item> LIQUID_ESSENCE_BUCKET = ITEMS.register(
            "liquid_essence_bucket",
            () -> new BucketItem(ModFluids.LIQUID_ESSENCE, BUCKET_PROPERTIES)
    );
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE_FLOWING = FLUIDS.register(
            "liquid_essence_flowing",
            LiquidEssenceFluid.Flowing::new
    );
    public static final ForgeFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.LIQUID_ESSENCE,
            ModFluids.LIQUID_ESSENCE_FLOWING,
            FluidAttributes.builder(
                    new ResourceLocation("minecraft", "block/water_still"),
                    new ResourceLocation("minecraft", "block/water_flow")
            )
                    .color(new Color(0x74FFFC).getRGB())
                    .luminosity(3)
    )
            .bucket(ModFluids.LIQUID_ESSENCE_BUCKET)
            .block(LIQUID_ESSENCE_BLOCK);

    //TODO remove java.awt.Color
    public static void register() {
    }
}
