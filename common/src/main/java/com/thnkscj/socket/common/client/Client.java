package com.thnkscj.socket.common.client;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.event.common.EventClientConnect;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.common.util.stream.InputStreamThread;
import com.thnkscj.socket.common.util.stream.OutputStreamThread;

import java.io.IOException;
import java.net.Socket;

/**
 * A wrapper for a socket connection.
 */
public class Client extends Connection {
    /**
     * The hostname and port to connect to. (Client only)
     */
    private String hostname;
    /**
     * The port to connect to. (Client only)
     */
    private int port;

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
     * Whether this client is a server.
     */
    private final boolean server;

    /**
     * Whether this client has received an exchange packet. (Client only, stops the client from sending packets before the server has sent an exchange packet)
     */
    private boolean hasExchangePacket = false;

    /**
     * Logger
     */

    public final Logger LOGGER = Logger.getLogger("Client");

    /**
     * Creates a new client.
     *
     * @param hostname The hostname to connect to.
     * @param port    The port to connect to.
     */
    public Client(final String hostname, final int port) {
        this.hostname = hostname;
        this.port = port;
        this.server = false;
    }

    /**
     * Creates a new client.
     *
     * @param socket The socket to connect to.
     */
    public Client(final Socket socket) {
        this.socket = socket;
        this.server = true;
    }

    /**
     * Gets the hostname.
     *
     * @return The hostname.
     */
    public boolean isServer() {
        return server;
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
     * Gets the hostname.
     *
     * @return The hostname.
     */
    public boolean hasExchangePacket() {
        return hasExchangePacket;
    }

    /**
     * Sets whether this client has received an exchange packet.
     *
     * @param hasExchangePacket Whether this client has received an exchange packet.
     */
    public void setHasExchangePacket(boolean hasExchangePacket) {
        this.hasExchangePacket = hasExchangePacket;
    }

    /**
     * Called when the client connects.
     */
    @Override
    public void connect() throws IOException {
        if (this.isServer()) {
            ServerEventBus.EVENT_BUS.post(new EventClientConnect(this));
        } else {
            ClientEventBus.EVENT_BUS.post(new EventClientConnect(this));
        }

        if (this.socket == null) {
            this.socket = new Socket(this.hostname, this.port);
            this.socket.setKeepAlive(true);
        }

        this.inputStreamThread = new InputStreamThread(this);
        this.inputStreamThread.run();
        this.outputStreamThread = new OutputStreamThread(this);
        this.outputStreamThread.run();
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
        if (!this.hasExchangePacket && !this.isServer())
            return;

        this.outputStreamThread.send(packet);
    }
}
