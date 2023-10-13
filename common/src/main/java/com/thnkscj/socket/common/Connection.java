package com.thnkscj.socket.common;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Manage the connection between the client and the server.
 */
public abstract class Connection {
    /**
     * The connection UUID.
     */
    private final AtomicReference<UUID> connectionUUID = new AtomicReference<>(UUID.randomUUID());

    /**
     * Get the connection UUID.
     *
     * @return The connection UUID.
     */
    public AtomicReference<UUID> getConnectionUUID() {
        return this.connectionUUID;
    }

    /**
     * Called when the connection is needed to be established.
     */
    public abstract void connect() throws IOException;

    /**
     * Called when the connection is needed to be closed.
     */
    public abstract void disconnect() throws IOException;
}
