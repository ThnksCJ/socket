package com.thnkscj.socket.server;

import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;
import com.thnkscj.socket.common.util.Logger;
import com.thnkscj.socket.server.network.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to accept new clients and add them to the client list.
 * It also provides methods to send packets to clients.
 *
 * @see Client
 */
public class ServerSocketAcceptingThread extends Thread {

    /**
     * This list contains all connected clients.
     */
    private static final List<Client> clients = new ArrayList<>();

    /**
     * The server socket.
     */
    private final ServerSocket serverSocket;

    /**
     * Logger
     */
    private final Logger LOGGER = Logger.getLogger("Server/AcceptingThread");

    /**
     * Creates a new instance of this class.
     *
     * @param serverSocket The server socket.
     */
    public ServerSocketAcceptingThread(final ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Returns a client by its UUID.
     *
     * @return The client.
     */
    public static Client getClient(UUID uuid) {
        for (Client client : clients) {
            if (client.getConnectionUUID().get().equals(uuid)) {
                return client;
            }
        }
        return null;
    }

    /**
     * This handles the accepting of new clients and sending {@link SPacketRequestExchange} to them.
     */
    @Override
    public void run() {
        try {
            while (true) {
                if (this.serverSocket.isClosed()) {
                    this.interrupt();
                    break;
                } else {
                    final Socket socket = this.serverSocket.accept();
                    final Client client = new Client(socket);
                    client.connect();
                    clients.add(client);

                    client.send(new SPacketRequestExchange(PacketRegistry.getRegisteredPackets().stream().map(Class::getSimpleName).toArray(String[]::new)));
                }
            }
        } catch (final IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    /**
     * Sends a packet to a client by its UUID.
     *
     * @param packet The packet.
     * @param uuid   The UUID of the client.
     */
    public void sendToClient(final Packet packet, final UUID uuid) {
        clients.stream().filter(client -> client.getConnectionUUID().get().equals(uuid)).forEach(client -> {
            LOGGER.debug("Sending packet " + packet.getClass().getSimpleName() + " to " + client.getConnectionUUID().get());
            client.send(packet);
        });
    }

    /**
     * Sends a packet to all clients.
     *
     * @param packet The packet.
     */
    public void sendToAllClients(final Packet packet) {
        clients.forEach(client -> client.send(packet));
    }

    /**
     * Disconnects a client by its UUID.
     *
     * @param uuid The UUID of the client.
     */
    public void disconnectClient(final UUID uuid) {
        clients.stream().filter(client -> client.getConnectionUUID().get().equals(uuid)).forEach(client -> {
            try {
                LOGGER.info("Disconnecting client " + client.getConnectionUUID().get());
                client.disconnect();
            } catch (final IOException exception) {
                LOGGER.error(exception.getMessage());
            }
        });
    }

    /**
     * Disconnects all clients.
     */
    public void disconnectAllClients() {
        LOGGER.info("Disconnecting all clients");
        clients.forEach(client -> {
            try {
                if (client != null) {
                    client.disconnect();
                }
            } catch (final IOException exception) {
                LOGGER.error(exception.getMessage());
            }
        });
        clients.clear();
    }
}
