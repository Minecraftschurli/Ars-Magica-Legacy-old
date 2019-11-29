package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.entity.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-25
 */
public class ModEntities implements IInit {
    public static final RegistryObject<EntityType<Entity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.create(SpellProjectileEntity::new, EntityClassification.MISC).build("spell_projectile"));
    public static final RegistryObject<EntityType<Entity>> THROWN_ROCK = ENTITIES.register("thrown_rock", () -> EntityType.Builder.create(ThrownRockEntity::new, EntityClassification.MISC).build("thrown_rock"));

    public static void register() {
    }
}
