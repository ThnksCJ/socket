package com.thnkscj.socket.common.event.server;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.util.annotation.Side;
import com.thnkscj.socket.common.util.annotation.SideOnly;

@SideOnly(Side.Server)
public class EventClientDisconnect extends Event {

    private final Client client;

    public EventClientDisconnect(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
