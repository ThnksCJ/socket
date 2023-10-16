package com.thnkscj.socket.client.packets.server;

import com.thnkscj.socket.client.MainClient;
import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

@SuppressWarnings("unused")
public class SPacketPong extends Packet {
    private static long currentTimeMillis;

    public SPacketPong(){}

    public SPacketPong(long currentTimeMillis) {
        SPacketPong.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer, Connection conn) {
        currentTimeMillis = readingByteBuffer.readLong();

        MainClient.ping = (System.currentTimeMillis() - currentTimeMillis);
    }
}
