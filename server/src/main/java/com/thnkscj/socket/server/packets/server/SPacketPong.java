package com.thnkscj.socket.server.packets.server;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public class SPacketPong extends Packet {
    public static long currentTimeMillis;

    public SPacketPong(UUID connectionUUID) {
        super(connectionUUID);
    }

    public SPacketPong(long currentTimeMillis) {
        super(null);
        SPacketPong.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeLong(currentTimeMillis);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
    }
}
