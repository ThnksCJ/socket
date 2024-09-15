package com.thnkscj.socket.client;

import com.thnkscj.socket.client.event.ClientEventBus;
import com.thnkscj.socket.client.event.events.EventClientConnect;
import com.thnkscj.socket.client.event.events.EventServerDisconnect;
import com.thnkscj.socket.client.network.Client;
import com.thnkscj.socket.client.packets.client.CPacketDisconnect;
import com.thnkscj.socket.client.packets.client.CPacketPing;
import com.thnkscj.skyliner.Subscribe;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainClient {
    public static long ping = 0;
    private static Client client;

    public static Client getClient() {
        return client;
    }

    public static void main(String[] args) throws IOException {
        ClientEventBus.EVENT_BUS.subscribe(MainClient.class);

        client = new Client("localhost", 19503);
        client.connect();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client.send(new CPacketDisconnect());
                Thread.sleep(2000);
                client.disconnect();
            } catch (InterruptedException | IOException e) {
                client.LOGGER.error("Error while disconnecting from server", e.getCause().getMessage());
            }
        }));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                client.send(new CPacketPing(System.currentTimeMillis()));
            }
        }, 0, 1500);
    }

    @Subscribe
    public static void onServerDisconnect(EventServerDisconnect event) {
        client.LOGGER.info("Disconnected from server, Reason: " + event.getReason());
    }

    @Subscribe
    public static void onClientConnect(EventClientConnect event) {
        client.LOGGER.info("Client connected to server");
    }
}