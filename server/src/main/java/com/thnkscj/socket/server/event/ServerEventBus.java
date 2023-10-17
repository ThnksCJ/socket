package com.thnkscj.socket.server.event;

import org.cubic.esys.EventBus;
import org.cubic.esys.EventDispatcher;

/**
 * This class should only be called from the server itself.
 * This class is here for event bus accessibility reasons.
 */
public final class ServerEventBus {
    public static final EventBus EVENT_BUS = EventDispatcher.builder().name("SBus").build();
}
