package com.thnkscj.socket.common.server;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.packet.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;

public class Server extends Connection {

    private static ServerSocketAcceptingThread serverSocketAcceptingThread;
    private final int port;
    private ServerSocket serverSocket;

    public Server(final int port) {
        this.port = port;

        System.out.println("[VCS] Server created!");
    }

    public static void sendToClient(final Packet packet, final UUID uuid) {
        serverSocketAcceptingThread.sendToClient(packet, uuid);
    }

    @Override
    public void connect() throws IOException {
        if (this.serverSocket == null) {
            this.serverSocket = new ServerSocket(this.port);
        }

        serverSocketAcceptingThread = new ServerSocketAcceptingThread(this.serverSocket);
        serverSocketAcceptingThread.start();
    }

    @Override
    public void disconnect() throws IOException {
        this.disconnectAllClients();

        if (!this.serverSocket.isClosed()) {
            this.serverSocket.close();
        }
    }

    public void sendToAllClients(final Packet packet) {
        serverSocketAcceptingThread.sendToAllClients(packet);
    }

    public void disconnectClient(final UUID uuid) {
        serverSocketAcceptingThread.disconnectClient(uuid);
    }

    public void disconnectAllClients() {
        serverSocketAcceptingThread.disconnectAllClients();
    }
}
