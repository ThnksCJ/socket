package com.thnkscj.socket.common.packet.packets;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.Arrays;
import java.util.UUID;

import static com.thnkscj.socket.common.util.Reflection.registerPackets;

public class SPacketRequestExchange extends Packet {
    private String[] packetList;

    public SPacketRequestExchange(UUID uuid) {
        super(uuid);
    }

    public SPacketRequestExchange(String[] packetList) {
        super(null);
        this.packetList = packetList;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeStringArray(packetList);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        packetList = readingByteBuffer.readStringArray();

        Arrays.stream(packetList).forEach(packetName -> {
            registerPackets(packetName, "com.thnkscj.socket.client.packets.client");
            registerPackets(packetName, "com.thnkscj.socket.client.packets.server");
        });

        LOGGER.info("Received packet list from server: " + Arrays.toString(packetList));
    }
}
