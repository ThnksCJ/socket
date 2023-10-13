package com.thnkscj.socket.server.packets.client;

import com.thnkscj.socket.common.event.server.EventClientDisconnect;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.common.server.ServerSocketAcceptingThread;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public class CPacketDisconnect extends Packet {
    public CPacketDisconnect(UUID connectionUUID) {
        super(connectionUUID);
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        if (readingByteBuffer.readLong() == 0xDEADC0E) {
            ServerEventBus.EVENT_BUS.post(new EventClientDisconnect(ServerSocketAcceptingThread.getClient(this.getConnectionUUID())));
        }
    }
}
