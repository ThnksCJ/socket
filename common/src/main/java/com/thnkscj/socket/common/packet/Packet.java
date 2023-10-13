package com.thnkscj.socket.common.packet;

import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.util.UUID;

/**
 * The packet is used to exchange data between the client and the server
 **/
public abstract class Packet {

    /**
     * The connectionUUID is used to identify the connection that is sending the packet
     */
    private final UUID connectionUUID;

    /**
     * The logger is used to log information about the packet
     */
    public final Logger LOGGER = Logger.getLogger(Packet.class.getSimpleName() + "/" + this.getClass().getSimpleName());

    /**
     * The constructor is used to create a new packet
     *
     * @param connectionUUID is used to identify the connection between the client and the server
     **/

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
