package com.thnkscj.socket.common.server;

import com.thnkscj.socket.common.util.annotation.Side;
import com.thnkscj.socket.common.util.annotation.SideOnly;
import org.cubic.esys.EventBus;
import org.cubic.esys.EventDispatcher;

/**
 * This class should only be called from the server it self.
 * This class is here for event bus accessibility reasons.
 */
@SideOnly(Side.Server)
public final class ServerEventBus {
    public static final EventBus EVENT_BUS = EventDispatcher.builder().name("SBus").build();
}
