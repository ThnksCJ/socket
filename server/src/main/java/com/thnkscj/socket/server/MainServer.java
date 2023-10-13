package com.thnkscj.socket.server;

import com.thnkscj.socket.common.event.common.EventClientConnect;
import com.thnkscj.socket.common.event.common.EventPacket;
import com.thnkscj.socket.common.event.server.EventClientDisconnect;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.server.Server;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.server.packets.client.*;
import com.thnkscj.socket.server.packets.server.*;
import org.cubic.esys.Subscribe;

import java.io.IOException;
import java.util.Arrays;

public class MainServer {
    private static Server server;
    public static Server getServer() {
        return server;
    }


    public static void main(String[] args) throws IOException {
        ServerEventBus.EVENT_BUS.subscribe(MainServer.class);

        // this must be exactly the same as on the client
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
                e.printStackTrace();
            }
        }));
    }

    @Subscribe
    public static void onClientDisconnect(EventClientDisconnect event) {
        System.out.println("[VCS] Client disconnected: " + event.getClient().getConnectionUUID().get());
    }

    @Subscribe
    public static void onClientConnect(EventClientConnect event) {
        System.out.println("[VCS] Client connected: " + event.getClient().getConnectionUUID().get());
    }
}
