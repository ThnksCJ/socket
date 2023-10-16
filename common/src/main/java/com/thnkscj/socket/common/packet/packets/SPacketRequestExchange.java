package com.thnkscj.socket.common.packet.packets;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.Arrays;
import java.util.UUID;

import static com.thnkscj.socket.common.util.Reflection.registerPackets;

/**
 * Server packet that tells the client what packets it can send to the server.
 */
@SuppressWarnings("unused")
public class SPacketRequestExchange extends Packet {
    private String[] packetList;

    public SPacketRequestExchange() {}

    public SPacketRequestExchange(String[] packetList) {
        this.packetList = packetList;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeStringArray(packetList);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer, Connection conn) {
        packetList = readingByteBuffer.readStringArray();

        Arrays.stream(packetList).forEach(packetName -> {
            registerPackets(packetName, "com.thnkscj.socket.client.packets.client");
            registerPackets(packetName, "com.thnkscj.socket.client.packets.server");
        });

        LOGGER.info("Received packet list from server: " + Arrays.toString(packetList));
    }
}
