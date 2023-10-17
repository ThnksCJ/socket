package com.thnkscj.socket.server.event.events;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.server.network.Client;

/**
 * Gets fired when a client disconnects from the server.
 */
public class EventClientDisconnect extends Event {

    private final Client client;

    public EventClientDisconnect(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
