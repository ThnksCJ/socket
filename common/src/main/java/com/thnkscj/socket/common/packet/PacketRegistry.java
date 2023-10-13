package com.thnkscj.socket.common.packet;

import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;

import java.util.ArrayList;
import java.util.List;

public class PacketRegistry {
    private static final List<Class<? extends Packet>> registeredPackets = new ArrayList<>();

    static {
        registerPacket(SPacketRequestExchange.class);
    }

    public static int indexOf(final Class<? extends Packet> packetClass) {
        return PacketRegistry.registeredPackets.indexOf(packetClass);
    }

    public static Class<? extends Packet> get(final int index) {
        return PacketRegistry.registeredPackets.get(index);
    }

    public static void registerPacket(final Class<? extends Packet> packetClass) {
        PacketRegistry.registeredPackets.add(packetClass);
    }

    public static void registerPackets(final List<Class<? extends Packet>> packetClasses) {
        PacketRegistry.registeredPackets.addAll(packetClasses);
    }

    public static List<Class<? extends Packet>> getRegisteredPackets() {
        return PacketRegistry.registeredPackets;
    }
}