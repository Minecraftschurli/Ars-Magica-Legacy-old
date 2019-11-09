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
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public class Fluids implements Registries {
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE = FLUIDS.register("liquid_essence", () -> new ForgeFlowingFluid.Source(Fluids.LIQUID_ESSENCE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> LIQUID_ESSENCE_FLOWING = FLUIDS.register("liquid_essence_flowing", () -> new ForgeFlowingFluid.Flowing(Fluids.LIQUID_ESSENCE_PROPERTIES));
    public static final RegistryObject<FlowingFluidBlock> LIQUID_ESSENCE_BLOCK = BLOCKS.register("liquid_essence_block", () -> new FlowingFluidBlock(Fluids.LIQUID_ESSENCE, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    public static final RegistryObject<Item> LIQUID_ESSENCE_BUCKET = ITEMS.register("liquid_essence_bucket", () -> new BucketItem(Fluids.LIQUID_ESSENCE, new Item.Properties().containerItem(net.minecraft.item.Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)));
    public static final ForgeFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(Fluids.LIQUID_ESSENCE, Fluids.LIQUID_ESSENCE_FLOWING, FluidAttributes.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_still")).color(0x74FFFC).luminosity(3)).bucket(Fluids.LIQUID_ESSENCE_BUCKET).block(LIQUID_ESSENCE_BLOCK);

//    public static final FluidImpl LIQUID_ESSENCE = new FluidImpl("liquid_essence", FluidAttributes.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_still")).color(0x74FFFC).luminosity(3));

    public static void register() {}

    /*public static class Fluid<F extends FlowingFluid, B extends FlowingFluidBlock, I extends BucketItem> {
        protected final RegistryObject<F> still;
        protected final RegistryObject<F> flowing;
        protected final RegistryObject<B> block;
        protected final RegistryObject<I> bucket;
        private ForgeFlowingFluid.Properties properties;

        public Fluid(String name, Function<ForgeFlowingFluid.Properties, F> still, Function<ForgeFlowingFluid.Properties, F> flowing, Function<Supplier<F>, B> block, Function<Supplier<F>, I> bucket, FluidAttributes.Builder attributes) {
            this.still = FLUIDS.register(name, () -> still.apply(properties));
            this.flowing = FLUIDS.register(name+"_flowing", () -> flowing.apply(properties));
            this.block = BLOCKS.register(name+"_block", () -> block.apply(this.still));
            this.bucket = ITEMS.register(name+"_bucket", () -> bucket.apply(this.still));
            this.properties = new ForgeFlowingFluid.Properties(this.still, this.flowing, attributes).bucket(this.bucket).block(this.block);
        }

        public BucketItem getBucket() {
            return bucket.get();
        }

        public FlowingFluid getStill() {
            return still.get();
        }

        public FlowingFluid getFlowing() {
            return flowing.get();
        }

        public FlowingFluidBlock getBlock() {
            return block.get();
        }
    }

    public static class FluidImpl extends Fluid<FlowingFluid, FlowingFluidBlock, BucketItem> {

        public FluidImpl(String name, FluidAttributes.Builder attributes) {
            super(
                    name,
                    ForgeFlowingFluid.Source::new,
                    ForgeFlowingFluid.Flowing::new,
                    still -> new FlowingFluidBlock(still, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()),
                    still -> new BucketItem(still, new Item.Properties().containerItem(net.minecraft.item.Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)),
                    attributes
            );
        }
    }*/
}
