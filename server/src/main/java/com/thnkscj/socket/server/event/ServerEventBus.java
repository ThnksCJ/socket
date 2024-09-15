package com.thnkscj.socket.server.event;

import com.thnkscj.skyliner.EventBus;
import com.thnkscj.skyliner.EventDispatcher;

/**
 * This class should only be called from the server itself.
 * This class is here for event bus accessibility reasons.
 */
public final class ServerEventBus {
    public static final EventBus EVENT_BUS = EventDispatcher.builder().name("SBus").build();
}
