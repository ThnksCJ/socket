package com.thnkscj.socket.client.packets.client;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

@SuppressWarnings("unused")
public class CPacketPing extends Packet {
    private static long currentTimeMillis;

    public CPacketPing(){}

    public CPacketPing(long currentTimeMillis) {
        CPacketPing.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeLong(currentTimeMillis);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer, Connection conn) {
    }
}
