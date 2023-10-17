package com.thnkscj.socket.server.packets.client;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;
import com.thnkscj.socket.server.ServerSocketAcceptingThread;
import com.thnkscj.socket.server.event.ServerEventBus;
import com.thnkscj.socket.server.event.events.EventClientDisconnect;

@SuppressWarnings("unused")
public class CPacketDisconnect extends Packet {

    public CPacketDisconnect() {
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer, Connection conn) {
        if (readingByteBuffer.readLong() == 0xDEADC0E) {
            ServerEventBus.EVENT_BUS.post(new EventClientDisconnect(ServerSocketAcceptingThread.getClient(conn.getConnectionUUID().get())));
        }
    }
}
