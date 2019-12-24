package minecraftschurli.arsmagicalegacy.objects.particle;

import minecraftschurli.arsmagicalegacy.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public final class ParticleRenderer {
    public static void spawnParticle(World world, String location, double x, double y, double z, double targetX, double targetY, double targetZ, boolean isVanilla) {
        if (!world.isRemote) {
            StandardParticle particle = new StandardParticle(world, x, y, z);
            particle.addMotion(targetX, targetY, targetZ);
            particle.setTexture(new ResourceLocation(isVanilla ? "minecraft:particle/" + location : ArsMagicaLegacy.MODID + ":particle/" + location));
            world.addParticle(particle, x, y, z, targetX, targetY, targetZ);
        }
    }

    public static void spawnParticle(World world, String location, double x, double y, double z, Entity target, boolean isVanilla) {
        spawnParticle(world, location, x, y, z, target.posX, target.posY, target.posZ, isVanilla);
    }

    public static void spawnParticle(World world, String location, Entity source, Entity target, boolean isVanilla) {
        spawnParticle(world, location, source.posX, source.posY, source.posZ, target.posX, target.posY, target.posZ, isVanilla);
    }

    public static void spawnBolt(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int type, int color, int damage) {
        if (!world.isRemote) {
            BoltParticle particle = new BoltParticle(world, startX, startY, startZ, endX, endY, endZ);
            particle.setType(type);
            particle.setDamage(damage);
            particle.setOverrideColor(color);
            world.addParticle(particle, startX, startY, startZ, endX, endY, endZ);
        }
    }

    public static void spawnBolt(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int type, int color) {
        spawnBolt(world, startX, startY, startZ, endX, endY, endZ, type, color, 1);
    }

    public static void spawnBeam(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int color) {
        if (!world.isRemote) {
            BeamParticle particle = new BeamParticle(world, startX, startY, startZ, endX, endY, endZ);
            particle.setColor(color);
            world.addParticle(particle, startX, startY, startZ, endX, endY, endZ);
        }
    }

    public static void spawnBeam(World world, double startX, double startY, double startZ, double endX, double endY, double endZ) {
        spawnBeam(world, startX, startY, startZ, endX, endY, endZ, 0xFFFFFF);
    }

    public static void spawnBeam(World world, Entity source, double endX, double endY, double endZ) {
        spawnBeam(world, source, endX, endY, endZ, 0xFFFFFF);
    }

    public static void spawnBeam(World world, Entity source, double endX, double endY, double endZ, int color) {
        if (!(source instanceof PlayerEntity) || source == ArsMagicaLegacy.proxy.getLocalPlayer())
            spawnBeam(world, source.posX, source.posY, source.posZ, endX, endY, endZ, color);
        else
            spawnBeam(world, source.posX, source.posY + source.getEyeHeight() - 0.2f, source.posZ, endX, endY, endZ, color);
    }

    public static void spawnBeam(World world, Entity source, Entity target, int color) {
        spawnBeam(world, source, target.posX, target.posY, target.posZ, color);
    }

    public static void spawnBeam(World world, Entity source, Entity target) {
        spawnBeam(world, source, target.posX, target.posY, target.posZ, 0xFFFFFF);
    }

    public static void spawnRibbon(World world, Entity source, float width) {
        spawnRibbon(world, source, width, 0xFFFFFF);
    }

    public static void spawnRibbon(World world, double x, double y, double z, float width) {
        spawnRibbon(world, x, y, z, width, 0xFFFFFF);
    }

    public static void spawnRibbon(World world, Entity source, float width, int color) {
        spawnRibbon(world, source.posX, source.posY, source.posZ, width, color);
    }

    public static void spawnRibbon(World world, double x, double y, double z, float width, int color) {
        if (!world.isRemote) {
            RibbonParticle particle = new RibbonParticle(world, x, y, z, width, color);
            world.addParticle(particle, x, y, z, 0, 0, 0);
        }
    }
}
