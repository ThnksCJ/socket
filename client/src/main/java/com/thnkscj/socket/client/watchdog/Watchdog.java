package com.thnkscj.socket.client.watchdog;

import com.thnkscj.socket.client.event.ClientEventBus;
import com.thnkscj.socket.client.event.events.EventServerReachable;
import com.thnkscj.socket.client.network.Client;
import com.thnkscj.socket.common.util.Logger;

import java.net.Socket;

public class Watchdog {
    private final Client client;
    private static Logger LOGGER = Logger.getLogger("Watchdog");

    public Watchdog(Client client) {
        this.client = client;
    }

    public static void startWatchdog(String host, int port) {
        while (true) {
            try {
                Socket socket = new Socket(host, port);

                if (socket.isConnected()) {
                    break;
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                }
            }
        }

        ClientEventBus.EVENT_BUS.post(new EventServerReachable());
    }
}
