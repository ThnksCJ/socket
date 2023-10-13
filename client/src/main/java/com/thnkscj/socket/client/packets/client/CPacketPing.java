package com.thnkscj.socket.client.packets.client;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public class CPacketPing extends Packet {
    private static long currentTimeMillis;

    public CPacketPing(UUID connectionUUID) {
        super(connectionUUID);
    }

    public CPacketPing(long currentTimeMillis) {
        super(null);
        CPacketPing.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeLong(currentTimeMillis);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
    }
}
