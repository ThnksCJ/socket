package com.thnkscj.socket.server.packets.client;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;
import com.thnkscj.socket.server.network.Server;
import com.thnkscj.socket.server.packets.server.SPacketPong;

@SuppressWarnings("unused")
public class CPacketPing extends Packet {

    public CPacketPing() {
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer, Connection conn) {
        long currentTimeMillis = readingByteBuffer.readLong();

        Server.sendToClient(new SPacketPong(currentTimeMillis), conn.getConnectionUUID().get());
    }
}
