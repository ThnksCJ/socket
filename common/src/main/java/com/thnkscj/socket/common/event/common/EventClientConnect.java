package com.thnkscj.socket.common.event.common;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.util.annotation.Side;
import com.thnkscj.socket.common.util.annotation.SideOnly;

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
