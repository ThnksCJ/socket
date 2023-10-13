package com.thnkscj.socket.client;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.client.ClientEventBus;
import com.thnkscj.socket.common.event.common.EventClientConnect;
import com.thnkscj.socket.common.event.client.EventServerDisconnect;
import com.thnkscj.socket.common.event.common.EventPacket;
import com.thnkscj.socket.client.packets.client.*;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;
import com.thnkscj.socket.common.util.Logger;
import org.cubic.esys.Subscribe;

import java.io.IOException;

public class MainClient {
    private static Client client;
    public final Logger LOGGER = Logger.getLogger("Client");
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
                client.send(new CPacketDisconnect(getClient().getConnectionUUID().get()));
                Thread.sleep(2000);
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));


        /*
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                client.send(new CPacketPing(System.currentTimeMillis()));
            }
        }, 0, 1500);

         */
    }

    @Subscribe
    public static void onPacket(EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketRequestExchange) {
            ClientEventBus.EVENT_BUS.post(new EventClientConnect(client));
            client.setHasExchangePacket(true);
        }
    }

    @Subscribe
    public static void onServerDisconnect(EventServerDisconnect event){
        System.out.println("Disconnected from server, Reason: " + event.getReason());
    }

    @Subscribe
    public static void onClientConnect(EventClientConnect event){
        System.out.println("Client connected: " + event.getClient().getConnectionUUID().get().toString());
    }
}