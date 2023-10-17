package com.thnkscj.socket.client.event.events;

import com.thnkscj.socket.client.network.Client;
import com.thnkscj.socket.common.event.Event;

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
