package com.thnkscj.socket.common.event.client;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.event.annotation.Side;
import com.thnkscj.socket.common.event.annotation.SideOnly;

/**
 * Gets fired when the client disconnects from the server. (Usually because of a disconnect or server shutdown)
 */
@SideOnly(Side.Client)
public class EventServerDisconnect extends Event {
    private final String reason;

    public EventServerDisconnect(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
