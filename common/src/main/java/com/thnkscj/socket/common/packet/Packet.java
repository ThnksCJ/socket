package com.thnkscj.socket.common.packet;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

/**
 * The packet is used to exchange data between the client and the server
 **/
public abstract class Packet {

    /**
     * The logger is used to log information about the packet
     */
    public final Logger LOGGER = Logger.getLogger(Packet.class.getSimpleName() + "/" + this.getClass().getSimpleName());

    /**
     * The writingByteBuffer is used to write data to the other side. e.g exchange data client -> server
     **/
    public abstract void send(WritingByteBuffer writingByteBuffer);

    /**
     * The readingByteBuffer is used to read data from the other side. e.g read data
     *
     * @param readingByteBuffer the current packet data buffer.
     * @param conn              The client that sent the packet (for easy replying)
     **/
    public abstract void receive(ReadingByteBuffer readingByteBuffer, Connection conn);


    /**
     * The timestamp is used to identify the time when the packet was sent
     *
     * @return timestamp of the packet
     **/
    public long getTimestamp() {
        return System.currentTimeMillis();
    }
}
