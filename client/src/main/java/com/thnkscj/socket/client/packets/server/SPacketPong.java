package com.thnkscj.socket.client.packets.server;

import com.thnkscj.socket.client.MainClient;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public class SPacketPong extends Packet {
    private static long currentTimeMillis;

    public SPacketPong(UUID connectionUUID) {
        super(connectionUUID);
    }

    public SPacketPong(long currentTimeMillis) {
        super(null);
        SPacketPong.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        currentTimeMillis = readingByteBuffer.readLong();

        MainClient.ping = (System.currentTimeMillis() - currentTimeMillis);
    }
}
