package com.thnkscj.socket.server.packets.client;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.server.Server;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;
import com.thnkscj.socket.server.packets.server.SPacketPong;

import java.util.UUID;

public class CPacketPing extends Packet {
    public CPacketPing(UUID connectionUUID) {
        super(connectionUUID);
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        long currentTimeMillis = readingByteBuffer.readLong();

        Server.sendToClient(new SPacketPong(currentTimeMillis), getConnectionUUID());
    }
}
