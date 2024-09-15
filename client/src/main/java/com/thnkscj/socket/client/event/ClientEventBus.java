package com.thnkscj.socket.client.event;

import com.thnkscj.skyliner.EventBus;
import com.thnkscj.skyliner.EventDispatcher;

/**
 * This class should only be called from the client itself.
 * This class is here for event bus accessibility reasons.
 */
public final class ClientEventBus {

    public static final EventBus EVENT_BUS = EventDispatcher.builder().name("CBus").build();
}
