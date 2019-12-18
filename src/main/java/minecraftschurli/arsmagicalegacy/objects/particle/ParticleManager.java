package minecraftschurli.arsmagicalegacy.objects.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;

public class ParticleManager {
    public void spawn(World world, double x, double y, double z, double targetX, double targetY, double targetZ) {
        if (!world.isRemote) {
            StandardParticle particle = new StandardParticle(world, x, y, z);
            particle.addMotion(targetX, targetY, targetZ);
            spawn(world, particle);
        }
    }

    public void spawn(World world, double x, double y, double z, Entity target) {
        spawn(world, x, y, z, target.posX, target.posY, target.posZ);
    }

    public void spawn(World world, String name, Entity source, Entity target) {
        spawn(world, source.posX, source.posY, source.posZ, target.posX, target.posY, target.posZ);
    }

    public void spawn(World world, StandardParticle particle) {
        world.addParticle(particle, particle.getPosX(), particle.getPosY(), particle.getPosZ(), particle.getMotionX(), particle.getMotionY(), particle.getMotionZ());
    }

//    public void boltFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage, int type, int color) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(caster.getEntityId());
//            writer.add(source.getEntityId());
//            writer.add(target.getEntityId());
//            writer.add(damage);
//            writer.add(type);
//            writer.add(color);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), caster.posX, caster.posY, caster.posZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
//
//    public void boltFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ) {
//        boltFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 1, -1);
//    }
//
//    public void boltFromEntityToPoint(World world, Entity source, double endX, double endY, double endZ) {
//        boltFromEntityToPoint(world, source, endX, endY, endZ, 0, -1);
//    }
//
//    public void boltFromEntityToPoint(World world, Entity source, double endX, double endY, double endZ, int type, int color) {
//        boltFromPointToPoint(world, source.posX, source.posY + source.getEyeHeight(), source.posZ, endX, endY, endZ, type, color);
//    }
//
//    public void boltFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage) {
//        boltFromEntityToEntity(world, caster, source, target, damage, 1, -1);
//    }
//
//    public void boltFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int type, int color) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(startX);
//            writer.add(startY);
//            writer.add(startZ);
//            writer.add(endX);
//            writer.add(endY);
//            writer.add(endZ);
//            writer.add(type);
//            writer.add(color);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), startX, startY, startZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
//
//    public void beamFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage, int color) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(caster.getEntityId());
//            writer.add(source.getEntityId());
//            writer.add(target.getEntityId());
//            writer.add(damage);
//            writer.add(color);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), caster.posX, caster.posY, caster.posZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
//
//    public void beamFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int color) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(startX);
//            writer.add(startY);
//            writer.add(startZ);
//            writer.add(endX);
//            writer.add(endY);
//            writer.add(endZ);
//            writer.add(color);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), startX, startY, startZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
//
//    public void beamFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ) {
//        beamFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 0xFFFFFF);
//    }
//
//    public void beamFromEntityToPoint(World world, Entity source, double endX, double endY, double endZ) {
//        beamFromEntityToPoint(world, source, endX, endY, endZ, 0xFFFFFF);
//    }
//
//    public void beamFromEntityToPoint(World world, Entity source, double endX, double endY, double endZ, int color) {
//        if (!(source instanceof PlayerEntity) || source == ArsMagicaLegacy.proxy.getLocalPlayer()) beamFromPointToPoint(world, source.posX, source.posY, source.posZ, endX, endY, endZ, color);
//        else beamFromPointToPoint(world, source.posX, source.posY + source.getEyeHeight() - 0.2f, source.posZ, endX, endY, endZ, color);
//    }
//
//    public void beamFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage) {
//        beamFromEntityToEntity(world, caster, source, target, damage, 0xFFFFFF);
//    }
//
//    public void ribbonFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage, int type) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(caster.getEntityId());
//            writer.add(source.getEntityId());
//            writer.add(target.getEntityId());
//            writer.add(damage);
//            writer.add(type);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), caster.posX, caster.posY, caster.posZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
//
//    public void ribbonFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ) {
//        ribbonFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 1);
//    }
//
//    public void ribbonFromEntityToPoint(World world, Entity source, double endX, double endY, double endZ) {
//        ribbonFromEntityToPoint(world, source, endX, endY, endZ);
//    }
//
//    public void ribbonFromEntityToEntity(World world, Entity caster, Entity source, Entity target, int damage) {
//        ribbonFromEntityToEntity(world, caster, source, target, damage, 1);
//    }
//
//    public void ribbonFromPointToPoint(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, int type) {
//        if (!world.isRemote) {
//            AMDataWriter writer = new AMDataWriter();
//            writer.add(startX);
//            writer.add(startY);
//            writer.add(startZ);
//            writer.add(endX);
//            writer.add(endY);
//            writer.add(endZ);
//            writer.add(type);
//            AMNetHandler.INSTANCE.sendPacketToAllClientsNear(world.provider.getDimension(), startX, startY, startZ, 32, AMPacketIDs.PARTICLE_SPAWN_SPECIAL, writer.generate());
//        }
//    }
}
