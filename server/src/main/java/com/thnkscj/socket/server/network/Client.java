package com.thnkscj.socket.server.network;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.packet.packets.conn.NPacketRegister;
import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.server.event.ServerEventBus;
import com.thnkscj.socket.server.event.events.EventClientConnect;
import com.thnkscj.socket.server.stream.InputStreamThread;
import com.thnkscj.socket.server.stream.OutputStreamThread;

import java.io.IOException;
import java.net.Socket;

/**
 * A wrapper for a socket connection.
 */
public class Client extends Connection {
    /**
     * Logger
     */

    public final Logger LOGGER = Logger.getLogger("Client");

    /**
     * The socket to connect to. (Server only)
     */
    private Socket socket;
    /**
     * The input stream thread. (Used for receiving packets)
     */
    private InputStreamThread inputStreamThread;
    /**
     * The output stream thread. (Used for sending packets)
     */
    private OutputStreamThread outputStreamThread;

    /**
     * Creates a new client.
     *
     * @param socket The socket to connect to.
     */
    public Client(final Socket socket) {
        this.socket = socket;
    }

    /**
     * Gets the hostname.
     *
     * @return The hostname.
     */
    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Called when the client connects.
     */
    @Override
    public void connect() throws IOException {
        ServerEventBus.EVENT_BUS.post(new EventClientConnect(this));

        this.inputStreamThread = new InputStreamThread(this);
        this.inputStreamThread.run();
        this.outputStreamThread = new OutputStreamThread(this);
        this.outputStreamThread.run();

        send(new NPacketRegister(PacketRegistry.getRegisteredPacketNames().toArray(new String[0])));
    }

    /**
     * Called when the client disconnects.
     */
    @Override
    public void disconnect() throws IOException {
        this.inputStreamThread.interrupt();
        this.outputStreamThread.interrupt();

        if (!this.socket.isClosed()) {
            this.socket.close();
        }
    }

    /**
     * Sends a packet.
     *
     * @param packet The packet to send.
     */
    public void send(final Packet packet) {
        this.outputStreamThread.send(packet);
    }
}
