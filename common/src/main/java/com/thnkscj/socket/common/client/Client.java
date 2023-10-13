package com.thnkscj.socket.common.client;

import com.thnkscj.socket.common.Connection;
import com.thnkscj.socket.common.event.common.EventClientConnect;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.common.util.stream.InputStreamThread;
import com.thnkscj.socket.common.util.stream.OutputStreamThread;

import java.io.IOException;
import java.net.Socket;

public class Client extends Connection {
    private String hostname;
    private int port;
    private Socket socket;
    private InputStreamThread inputStreamThread;
    private OutputStreamThread outputStreamThread;
    private final boolean server;
    private boolean hasExchangePacket = false;

    public Client(final String hostname, final int port) {
        this.hostname = hostname;
        this.port = port;
        this.server = false;

        // If we post here then the client starts with a mismatched uuid
        //ClientEventBus.EVENT_BUS.post(new EventClientConnect(this));
    }

    public Client(final Socket socket) {
        this.socket = socket;
        this.server = true;

        ServerEventBus.EVENT_BUS.post(new EventClientConnect(this));
    }

    public boolean isServer() {
        return server;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public boolean hasExchangePacket() {
        return hasExchangePacket;
    }

    public void setHasExchangePacket(boolean hasExchangePacket) {
        this.hasExchangePacket = hasExchangePacket;
    }

    @Override
    public void connect() throws IOException {
        if (this.socket == null) {
            this.socket = new Socket(this.hostname, this.port);
            this.socket.setKeepAlive(true);
        }

        this.inputStreamThread = new InputStreamThread(this);
        this.inputStreamThread.run();
        this.outputStreamThread = new OutputStreamThread(this);
        this.outputStreamThread.run();
    }

    @Override
    public void disconnect() throws IOException {
        this.inputStreamThread.interrupt();
        this.outputStreamThread.interrupt();

        if (!this.socket.isClosed()) {
            this.socket.close();
        }
    }

    public void send(final Packet packet) {
        if (!this.hasExchangePacket && !this.isServer())
            return;

        this.outputStreamThread.send(packet);
    }
}
