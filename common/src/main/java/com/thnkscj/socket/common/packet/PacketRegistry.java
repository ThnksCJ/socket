package com.thnkscj.socket.common.packet;

import com.thnkscj.socket.common.packet.packets.conn.NPacketRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Registry for all packets. Used in both client and server.
 */
public class PacketRegistry {
    /**
     * List of all registered packets.
     */
    private static final List<Class<? extends Packet>> registeredPackets = new ArrayList<>();

    static {
        registerPacket(NPacketRegister.class);
    }

    /**
     * Returns the index of the packet in the list of registered packets.
     *
     * @param packetClass The packet class.
     * @return The index of the packet in the list of registered packets or -1 if the packet is not registered.
     */
    public static int indexOf(final Class<? extends Packet> packetClass) {
        return OptionalInt.of(PacketRegistry.registeredPackets.indexOf(packetClass)).orElse(-1);
    }

    /**
     * Returns the packet class at the specified index.
     *
     * @param index The index of the packet class.
     * @return The packet class at the specified index.
     */
    public static Class<? extends Packet> get(final int index) {
        return PacketRegistry.registeredPackets.get(index);
    }

    /**
     * Registers a single packet.
     *
     * @param packetClass The packet.
     */
    public static void registerPacket(final Class<? extends Packet> packetClass) {
        PacketRegistry.registeredPackets.add(packetClass);
    }

    /**
     * Registers a list of packets.
     *
     * @param packetClasses The list of packets.
     */
    public static void registerPackets(final List<Class<? extends Packet>> packetClasses) {
        PacketRegistry.registeredPackets.addAll(packetClasses);
    }

    /**
     * Returns the list of registered packets.
     *
     * @return The list of registered packets.
     */
    public static List<Class<? extends Packet>> getRegisteredPackets() {
        return PacketRegistry.registeredPackets;
    }

    public static List<String> getRegisteredPacketNames() {
        List<String> packetNames = new ArrayList<>();
        for (Class<? extends Packet> packetClass : PacketRegistry.registeredPackets) {
            packetNames.add(getPacketName(packetClass));
        }
        return packetNames;
    }

    public static String getPacketName(Class<? extends Packet> packetClass) {
        String packetName = packetClass.getSimpleName();
        String packetHeader = packetClass.getPackageName().split("\\.")[packetClass.getPackageName().split("\\.").length - 1];
        return packetHeader + "." + packetName;
    }
}