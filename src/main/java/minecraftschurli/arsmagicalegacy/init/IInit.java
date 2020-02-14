package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.fluid.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;
import net.minecraft.particles.*;
import net.minecraft.potion.*;
import net.minecraft.tileentity.*;
import net.minecraft.village.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-07
 */
public interface IInit {
    DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ArsMagicaLegacy.MODID);
    DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, ArsMagicaLegacy.MODID);
    DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ArsMagicaLegacy.MODID);
    DeferredRegister<Effect> POTIONS = new DeferredRegister<>(ForgeRegistries.POTIONS, ArsMagicaLegacy.MODID);
    DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, ArsMagicaLegacy.MODID);
    DeferredRegister<Potion> POTION_TYPES = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, ArsMagicaLegacy.MODID);
    DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, ArsMagicaLegacy.MODID);
    DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ArsMagicaLegacy.MODID);
    DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, ArsMagicaLegacy.MODID);
    DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, ArsMagicaLegacy.MODID);
    DeferredRegister<VillagerProfession> PROFESSIONS = new DeferredRegister<>(ForgeRegistries.PROFESSIONS, ArsMagicaLegacy.MODID);
    DeferredRegister<PointOfInterestType> POI_TYPES = new DeferredRegister<>(ForgeRegistries.POI_TYPES, ArsMagicaLegacy.MODID);

    DeferredRegister<WorldCarver<?>> WORLD_CARVERS = new DeferredRegister<>(ForgeRegistries.WORLD_CARVERS, ArsMagicaLegacy.MODID);
    DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, ArsMagicaLegacy.MODID);

    static void setEventBus(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        FLUIDS.register(modEventBus);
        ITEMS.register(modEventBus);
        POTIONS.register(modEventBus);
        BIOMES.register(modEventBus);
        POTION_TYPES.register(modEventBus);
        ENTITIES.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        PARTICLE_TYPES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        PROFESSIONS.register(modEventBus);
        POI_TYPES.register(modEventBus);

        WORLD_CARVERS.register(modEventBus);
        FEATURES.register(modEventBus);
    }
}
