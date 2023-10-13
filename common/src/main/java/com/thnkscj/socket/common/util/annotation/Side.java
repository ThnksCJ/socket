package com.thnkscj.socket.common.util.annotation;

/**
 * Used for posting events to the correct side since the `common` module is shared between client and server.
 */
public enum Side {
    Client,
    Server
}
