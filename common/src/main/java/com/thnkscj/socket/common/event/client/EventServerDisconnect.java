package com.thnkscj.socket.common.event.client;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.util.annotation.Side;
import com.thnkscj.socket.common.util.annotation.SideOnly;

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
