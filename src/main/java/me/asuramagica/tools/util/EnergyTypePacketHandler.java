package me.asuramagica.tools.util;

import com.google.common.base.Preconditions;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketB;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketC;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketD;
import me.asuramagica.tools.util.Packets.ManaStone.ManaEnergyPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.*;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.*;

public final class EnergyTypePacketHandler {

    public static final String PROTOCOL_VERSION = Integer.toString(0);
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(AsuraMagicaMod.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        registerServer();
        registerClient();
        System.out.println("Packet Handlers Registered");
    }

    private static void registerServer() {
    }

    private static void registerClient() {
    	registerMessage(EnergyTypePacket.class, EnergyTypePacket::encode, EnergyTypePacket::decode, EnergyTypePacket::handle);
    	registerMessage(EnergyTypePacketB.class, EnergyTypePacketB::encode, EnergyTypePacketB::decode, EnergyTypePacketB::handle);
    	registerMessage(EnergyTypePacketC.class, EnergyTypePacketC::encode, EnergyTypePacketC::decode, EnergyTypePacketC::handle);
    	registerMessage(EnergyTypePacketD.class, EnergyTypePacketD::encode, EnergyTypePacketD::decode, EnergyTypePacketD::handle);
    	registerMessage(ManaEnergyPacket.class, ManaEnergyPacket::encode, ManaEnergyPacket::decode, ManaEnergyPacket::handle);
    }



    public static void sendTo(ServerPlayerEntity player, Object msg) {
        if (!(player instanceof FakePlayer)) {
            CHANNEL.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToServer(Object msg) {
        CHANNEL.sendToServer(msg);
    }

    private static int nextID = 0;

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        Preconditions.checkState(nextID < 0xFF, "Too many messages!");
        CHANNEL.registerMessage(nextID, messageType, encoder, decoder, handler);
        nextID++;
    }
}