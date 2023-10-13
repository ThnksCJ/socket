package com.thnkscj.socket.common;

import com.thnkscj.socket.common.packet.PacketRegistry;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Connection {
    private final AtomicReference<UUID> connectionUUID = new AtomicReference<>(UUID.randomUUID());
    private final PacketRegistry packetRegistry = new PacketRegistry();

    public AtomicReference<UUID> getConnectionUUID() {
        return this.connectionUUID;
    }

    public PacketRegistry getPacketRegistry() {
        return this.packetRegistry;
    }

    public abstract void connect() throws IOException;

    public abstract void disconnect() throws IOException;
}
