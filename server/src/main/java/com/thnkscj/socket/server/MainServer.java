package com.thnkscj.socket.server;

import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.server.event.ServerEventBus;
import com.thnkscj.socket.server.event.events.EventClientConnect;
import com.thnkscj.socket.server.event.events.EventClientDisconnect;
import com.thnkscj.socket.server.network.Server;
import com.thnkscj.socket.server.packets.client.CPacketDisconnect;
import com.thnkscj.socket.server.packets.client.CPacketPing;
import com.thnkscj.socket.server.packets.server.SPacketPong;
import org.cubic.esys.Subscribe;

import java.io.IOException;
import java.util.Arrays;

public class MainServer {
    private static Server server;

    public static void main(String[] args) throws IOException {
        ServerEventBus.EVENT_BUS.subscribe(MainServer.class);

        PacketRegistry.registerPackets(Arrays.asList(
                CPacketDisconnect.class,

                CPacketPing.class,
                SPacketPong.class
        ));

        server = new Server(19503);
        server.connect();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.disconnect();
            } catch (IOException e) {
                server.LOGGER.error("Failed to disconnect server", e.getCause().getMessage());
            }
        }));
    }

    @Subscribe
    public static void onClientDisconnect(EventClientDisconnect event) {
        server.LOGGER.info("Client disconnected: " + event.getClient().getConnectionUUID().get());
    }

    @Subscribe
    public static void onClientConnect(EventClientConnect event) {
        server.LOGGER.info("Client connected: " + event.getClient().getConnectionUUID().get());
    }
}
