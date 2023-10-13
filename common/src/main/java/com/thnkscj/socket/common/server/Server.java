package com.thnkscj.socket.common.server;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;

/**
 * Server class that extends Connection class.
 *
 * @see Connection
 */
public class Server extends Connection {

    /**
     * Used to accept clients.
     */
    private static ServerSocketAcceptingThread serverSocketAcceptingThread;

    /**
     * Port to listen to.
     */
    private final int port;

    /**
     * Server socket.
     */
    private ServerSocket serverSocket;

    /**
     * Logger
     */
    public final Logger LOGGER = Logger.getLogger("Server");

    /**
     * Constructor.
     *
     * @param port Port to listen to.
     */
    public Server(final int port) {
        this.port = port;

        LOGGER.info("Listening on port " + port);
    }

    /**
     * Sends a packet to a client.
     *
     * @param packet Packet to send.
     * @param uuid UUID of the client.
     */
    public static void sendToClient(final Packet packet, final UUID uuid) {
        serverSocketAcceptingThread.sendToClient(packet, uuid);
    }

    /**
     * Starts the server.
     *
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    @Override
    public void connect() throws IOException {
        if (this.serverSocket == null) {
            this.serverSocket = new ServerSocket(this.port);
        }

        serverSocketAcceptingThread = new ServerSocketAcceptingThread(this.serverSocket);
        serverSocketAcceptingThread.start();

        LOGGER.info("Started accepting clients");
    }

    /**
     * Stops the server.
     *
     * @throws IOException If an I/O error occurs when closing the socket.
     */
    @Override
    public void disconnect() throws IOException {
        this.disconnectAllClients();

        if (!this.serverSocket.isClosed()) {
            this.serverSocket.close();
        }

        LOGGER.info("Stopped accepting clients");
    }

    /**
     * Sends a packet to all clients.
     *
     * @param packet Packet to send.
     */
    public void sendToAllClients(final Packet packet) {
        serverSocketAcceptingThread.sendToAllClients(packet);
    }

    /**
     * Disconnects a client.
     *
     * @param uuid UUID of the client.
     */
    public void disconnectClient(final UUID uuid) {
        serverSocketAcceptingThread.disconnectClient(uuid);
    }

    /**
     * Disconnects all clients.
     */
    public void disconnectAllClients() {
        serverSocketAcceptingThread.disconnectAllClients();
    }
}
