package com.thnkscj.socket.server.event.events;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.server.network.Client;

/**
 * Gets fired when a client connects. This event is fired on both sides.
 */
public class EventClientConnect extends Event {

    private final Client client;

    public EventClientConnect(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
