package com.thnkscj.socket.client.network;

import com.thnkscj.socket.client.event.ClientEventBus;
import com.thnkscj.socket.client.event.events.EventClientConnect;
import com.thnkscj.socket.client.event.events.EventPacket;
import com.thnkscj.socket.client.stream.InputStreamThread;
import com.thnkscj.socket.client.stream.OutputStreamThread;
import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.skyliner.Subscribe;

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
     * Creates a new client.
     *
     * @param hostname The hostname to connect to.
     * @param port     The port to connect to.
     */
    public Client(final String hostname, final int port) {
        this.hostname = hostname;
        this.port = port;

        ClientEventBus.EVENT_BUS.subscribe(this);
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
        ClientEventBus.EVENT_BUS.post(new EventClientConnect(this));

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
        this.outputStreamThread.send(packet);
    }

    @Subscribe
    public void onPacketReceive(EventPacket.Receive event) {

    }
}
