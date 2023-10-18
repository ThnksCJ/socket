package com.thnkscj.socket.client.watchdog;

import com.thnkscj.socket.client.event.ClientEventBus;
import com.thnkscj.socket.client.event.events.EventServerDisconnect;
import com.thnkscj.socket.client.event.events.EventServerReachable;
import com.thnkscj.socket.client.network.Client;
import com.thnkscj.socket.common.util.Logger;
import org.cubic.esys.Subscribe;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WatchdogThread extends Thread {
    private final Client client;
    private int MAX_ATTEMPTS = 5;
    private int ATTEMPTS = 0;
    private Logger LOGGER = Logger.getLogger("Watchdog");

    public WatchdogThread(Client client) {
        super(String.format("Watchdog-%s", client.getConnectionUUID()));

        this.client = client;

        ClientEventBus.EVENT_BUS.subscribe(this);
    }

    @Subscribe
    public void onServerDisconnect(EventServerDisconnect event) throws InterruptedException {
        LOGGER.error("Server disconnected, pinging server...");
        pingServer(client.getHost(), client.getPort());
    }

    private void pingServer(String host, int port) throws InterruptedException {
        while (ATTEMPTS < MAX_ATTEMPTS) {
            try {
                Socket socket = new Socket(host, port);

                if (socket.isConnected()) {
                    LOGGER.info("Server is reachable");
                    break;
                }
            } catch (Exception e) {
                ATTEMPTS++;
                LOGGER.error("Server is not responding, attempt " + ATTEMPTS + " of " + MAX_ATTEMPTS);
                LOGGER.error("Retrying in 5 seconds...");
                Thread.sleep(5000);
            }
        }

        if (ATTEMPTS == MAX_ATTEMPTS) {
            LOGGER.error("Server is not responding, attempted to ping " + MAX_ATTEMPTS + " times.");
            return;
        }

        ClientEventBus.EVENT_BUS.post(new EventServerReachable());
    }
}
