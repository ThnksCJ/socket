package com.thnkscj.socket.common.server;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;
import org.boon.core.Sys;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ServerSocketAcceptingThread extends Thread {

    private static final List<Client> clients = new ArrayList<>();
    private final ServerSocket serverSocket;

    public ServerSocketAcceptingThread(final ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static Client getClient(UUID uuid) {
        for (Client client : clients) {
            if (client.getConnectionUUID().get().equals(uuid)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void run() {
        super.run();
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
            exception.printStackTrace();
        }
    }

    public void sendToClient(final Packet packet, final UUID uuid) {
        clients.stream().filter(client -> client.getConnectionUUID().get().equals(uuid)).forEach(client -> {
            System.out.println("[VCS] Sending packet: " + packet.getClass().getSimpleName() + " to client: " + client.getConnectionUUID().get());
            client.send(packet);
        });
    }

    public void sendToAllClients(final Packet packet) {
        clients.forEach(client -> client.send(packet));
    }

    public void disconnectClient(final UUID uuid) {
        clients.stream().filter(client -> client.getConnectionUUID().get().equals(uuid)).forEach(client -> {
            try {
                System.out.println("[VCS] Client: " + client.getConnectionUUID().get() + " will be disconnected!");
                client.disconnect();
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void disconnectAllClients() {
        System.out.println("[VCS] All Clients will be disconnected!");
        clients.forEach(client -> {
            try {
                if (client != null) {
                    client.disconnect();
                }
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        });
        clients.clear();
    }
}
