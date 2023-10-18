package com.thnkscj.socket.client.watchdog;

import com.thnkscj.socket.client.event.ClientEventBus;
import com.thnkscj.socket.client.network.Client;

public class Watchdog {
    public static void start(Client client) {
        new WatchdogThread(client);
    }
}
