package com.thnkscj.socket.client.packets.client;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public class CPacketDisconnect extends Packet {
    public CPacketDisconnect(UUID connectionUUID) {
        super(connectionUUID);
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        writingByteBuffer.writeLong(0xDEADC0E);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {

    }
}
