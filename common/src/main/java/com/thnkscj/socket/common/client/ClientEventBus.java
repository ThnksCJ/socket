package com.thnkscj.socket.common.client;

import com.thnkscj.socket.common.util.annotation.Side;
import com.thnkscj.socket.common.util.annotation.SideOnly;
import org.cubic.esys.EventBus;
import org.cubic.esys.EventDispatcher;

/**
 * This class should only be called from the client itself.
 * This class is here for event bus accessibility reasons.
 */
@SideOnly(Side.Client)
public final class ClientEventBus {

    public static final EventBus EVENT_BUS = EventDispatcher.builder().name("CBus").build();
}
