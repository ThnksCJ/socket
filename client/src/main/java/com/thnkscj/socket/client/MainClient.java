package com.thnkscj.socket.client;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.client.ClientEventBus;
import com.thnkscj.socket.common.event.common.EventClientConnect;
import com.thnkscj.socket.common.event.client.EventServerDisconnect;
import com.thnkscj.socket.common.event.common.EventPacket;
import com.thnkscj.socket.client.packets.client.*;
import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;
import org.cubic.esys.Subscribe;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainClient {
    private static Client client;
    public static long ping = 0;

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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
    public static void onPacket(EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketRequestExchange) {
            client.setHasExchangePacket(true);
        }
    }

    @Subscribe
    public static void onServerDisconnect(EventServerDisconnect event){
        client.LOGGER.info("Disconnected from server, Reason: " + event.getReason());
    }

    @Subscribe
    public static void onClientConnect(EventClientConnect event){
        client.LOGGER.info("Client connected to server");
    }
}