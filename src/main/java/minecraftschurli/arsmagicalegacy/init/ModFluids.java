package minecraftschurli.arsmagicalegacy.init;

import java.awt.Color;
import minecraftschurli.arsmagicalegacy.objects.fluid.LiquidEssenceFluid;
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
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public final class ModFluids implements IInit {
    public static final Item.Properties BUCKET_PROPERTIES = new Item.Properties().containerItem(net.minecraft.item.Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC);
    public static final Block.Properties FLUID_BLOCK_PROPERTIES = Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100).noDrops();
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE = FLUIDS.register("liquid_essence", LiquidEssenceFluid.Source::new);
    public static final RegistryObject<FlowingFluidBlock> LIQUID_ESSENCE_BLOCK = BLOCKS.register("liquid_essence_block", () -> new FlowingFluidBlock(ModFluids.LIQUID_ESSENCE, FLUID_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> LIQUID_ESSENCE_BUCKET = ITEMS.register("liquid_essence_bucket", () -> new BucketItem(ModFluids.LIQUID_ESSENCE, BUCKET_PROPERTIES));
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE_FLOWING = FLUIDS.register("liquid_essence_flowing", LiquidEssenceFluid.Flowing::new);
    public static final ForgeFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(ModFluids.LIQUID_ESSENCE, ModFluids.LIQUID_ESSENCE_FLOWING, FluidAttributes.builder(new ResourceLocation("minecraft", "block/water_still"), new ResourceLocation("minecraft", "block/water_flow")).color(new Color(0x74FFFC).getRGB()).luminosity(3)).bucket(ModFluids.LIQUID_ESSENCE_BUCKET).block(LIQUID_ESSENCE_BLOCK);
    //TODO remove java.awt.Color
    public static void register() {}
}
