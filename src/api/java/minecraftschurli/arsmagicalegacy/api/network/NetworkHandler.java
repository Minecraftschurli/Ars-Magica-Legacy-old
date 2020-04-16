package minecraftschurli.arsmagicalegacy.api.network;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public class NetworkHandler {
    public static final NetworkHandler INSTANCE = new NetworkHandler(ArsMagicaAPI.MODID, "main", 1);
    public final SimpleChannel channel;
    private final AtomicInteger id = new AtomicInteger();

    public NetworkHandler(String modid, String channelName, int protocol) {
        String protocolStr = protocol + "";
        this.channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(modid, channelName))
                .networkProtocolVersion(() -> protocolStr)
                .clientAcceptedVersions(protocolStr::equals)
                .serverAcceptedVersions(protocolStr::equals)
                .simpleChannel();
    }

    public int nextID() {
        synchronized (this.id) {
            return this.id.getAndIncrement();
        }
    }

    public <T extends IPacket> void register(Class<T> clazz, NetworkDirection dir) {
        Function<PacketBuffer, T> decoder = (buf) -> {
            try {
                T packet = clazz.newInstance();
                packet.deserialize(buf);
                return packet;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        BiConsumer<T, Supplier<NetworkEvent.Context>> consumer = (msg, supp) -> {
            NetworkEvent.Context context = supp.get();
            if (context == null) return;
            if (context.getDirection() != dir) return;
            context.setPacketHandled(msg.handle(context));
        };

        this.channel.registerMessage(nextID(), clazz, IPacket::serialize, decoder, consumer);
    }

    public void sendToWorld(IPacket packet, IWorld world) {
        if (world.isRemote()) return;
        ServerWorld sw = (ServerWorld) world;
        for (PlayerEntity player : sw.getPlayers()) {
            if (player.getEntityWorld() != world) continue;
            this.sendToPlayer(packet, player);
        }
    }

    public void sendToAllAround(IPacket packet, IWorld world, BlockPos pos, float radius) {
        if (world.isRemote()) return;
        ServerWorld sw = (ServerWorld) world;
        for (PlayerEntity player : sw.getPlayers()) {
            if (player.getPosition().distanceSq(pos) > radius * radius) continue;
            this.sendToPlayer(packet, player);
        }
    }

    public void sendToAllWatching(IPacket packet, IWorld world, BlockPos pos) {
        if (world.isRemote()) return;
        ChunkManager chunkManager = ((ServerWorld) world).getChunkProvider().chunkManager;
        chunkManager.getTrackingPlayers(new ChunkPos(pos), false).forEach(player -> {
            this.sendToPlayer(packet, player);
        });
    }

    public void sendToPlayer(IPacket packet, PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity)) return;
        this.channel.sendTo(packet, ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public void sendToServer(IPacket packet) {
        this.channel.sendToServer(packet);
    }

    public static void registerMessages() {
        INSTANCE.register(SyncManaPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(SyncBurnoutPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(SyncResearchPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(SyncMagicPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(SyncAffinityPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        INSTANCE.register(InscriptionTablePacket.class, NetworkDirection.PLAY_TO_SERVER);
        INSTANCE.register(TEClientSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        INSTANCE.register(UpdateStepHeightPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }
}
