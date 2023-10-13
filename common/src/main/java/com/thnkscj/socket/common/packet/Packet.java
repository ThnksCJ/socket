package com.thnkscj.socket.common.packet;

import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

public abstract class Packet {
    private final UUID connectionUUID;
    public final Logger LOGGER = Logger.getLogger(Packet.class.getSimpleName() + "/" + this.getClass().getSimpleName());

    public Packet(final UUID connectionUUID) {
        this.connectionUUID = connectionUUID;
    }

    /**
     * The connectionUUID is used to identify the connection between the client and the server
     *
     * @return UUID of the connection
     **/
    public UUID getConnectionUUID() {
        return this.connectionUUID;
    }

    /**
     * The writingByteBuffer is used to write data to the other side. e.g exchange data client -> server
     **/
    public abstract void send(WritingByteBuffer writingByteBuffer);

    /**
     * The readingByteBuffer is used to read data from the other side. e.g read data
     **/
    public abstract void receive(ReadingByteBuffer readingByteBuffer);


    /**
     * The timestamp is used to identify the time when the packet was sent
     *
     * @return timestamp of the packet
     **/
    public long getTimestamp() {
        return System.currentTimeMillis();
    }
}
