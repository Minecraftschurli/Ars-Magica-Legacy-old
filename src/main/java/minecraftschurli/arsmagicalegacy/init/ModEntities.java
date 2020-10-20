package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.entity.BlizzardEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.FireRainEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.ManaCreeperEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.ThrownRockEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.WallEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.WaveEntity;
import minecraftschurli.arsmagicalegacy.objects.entity.ZoneEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-25
 */
public final class ModEntities implements IInit {
    public static final RegistryObject<EntityType<Entity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.create(SpellProjectileEntity::new, EntityClassification.MISC).build("spell_projectile"));
    public static final RegistryObject<EntityType<Entity>> THROWN_ROCK = ENTITIES.register("thrown_rock", () -> EntityType.Builder.create(ThrownRockEntity::new, EntityClassification.MISC).build("thrown_rock"));
    public static final RegistryObject<EntityType<Entity>> BLIZZARD = ENTITIES.register("blizzard", () -> EntityType.Builder.create(BlizzardEntity::new, EntityClassification.MISC).build("blizzard"));
    public static final RegistryObject<EntityType<Entity>> FIRE_RAIN = ENTITIES.register("fire_rain", () -> EntityType.Builder.create(FireRainEntity::new, EntityClassification.MISC).build("fire_rain"));
    public static final RegistryObject<EntityType<Entity>> WALL = ENTITIES.register("wall", () -> EntityType.Builder.create(WallEntity::new, EntityClassification.MISC).build("wall"));
    public static final RegistryObject<EntityType<Entity>> WAVE = ENTITIES.register("wave", () -> EntityType.Builder.create(WaveEntity::new, EntityClassification.MISC).build("wave"));
    public static final RegistryObject<EntityType<Entity>> ZONE = ENTITIES.register("zone", () -> EntityType.Builder.create(ZoneEntity::new, EntityClassification.MISC).build("zone"));
    public static final RegistryObject<EntityType<ManaCreeperEntity>> MANA_CREEPER = ENTITIES.register("mana_creeper", () -> EntityType.Builder.<ManaCreeperEntity>create(ManaCreeperEntity::new, EntityClassification.MONSTER).build("mana_creeper"));

    public static void register() {
    }
}
