package com.thnkscj.socket.client.event.events;

import com.thnkscj.socket.common.event.Event;

/**
 * Gets fired when the client disconnects from the server. (Usually because of a disconnect or server shutdown)
 */
public class EventServerDisconnect extends Event {
    private final String reason;

    public EventServerDisconnect(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
