package com.thnkscj.socket.common.util.stream;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.client.ClientEventBus;
import com.thnkscj.socket.common.event.common.EventPacket;
import com.thnkscj.socket.common.event.server.EventClientDisconnect;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.packet.packets.SPacketRequestExchange;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.common.server.ServerSocketAcceptingThread;
import com.thnkscj.socket.common.util.bytes.ReadingByteBuffer;
import org.boon.core.Sys;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is used to read the input stream of a socket.
 * Handles:
 * - receiving packets
 * - posting  vents
 *
 * @author Thnks_CJ
 */
public class InputStreamThread {

    /**
     * The bytes that are read from the input stream.
     */
    final AtomicReference<byte[]> bytes = new AtomicReference<>(null);

    /**
     * The client that is connected to the socket.
     */
    private final Client client;

    /**
     * The underlying socket
     */
    private final Socket socket;

    /**
     * The timer that handles the reading of packets
     */
    private final Timer timer = new Timer();

    /**
     * The input stream
     */
    private InputStream finalInputStream;

    /**
     * Creates a new input stream thread
     *
     * @param client The client that is connected to the socket
     */
    public InputStreamThread(final Client client) {
        this.client = client;
        this.socket = this.client.getSocket();
    }

    /**
     * Starts the input stream thread
     *
     * @throws IOException if an I/O error occurs when creating the input stream
     */
    public void run() throws IOException {
        this.finalInputStream = this.socket.getInputStream();

        final long[] start = {0L};
        long threshold = 5000;

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (socket.isClosed()) {
                        interrupt();
                        return;
                    }

                    assert finalInputStream != null;

                    if (finalInputStream.available() > 0) {
                        final int b = finalInputStream.read();

                        if (b != -1) {
                            bytes.set(new byte[b]);
                            finalInputStream.read(bytes.get(), 0, b);

                            final ReadingByteBuffer readingByteBuffer = new ReadingByteBuffer(bytes.get());
                            final int packetId = readingByteBuffer.readInt();
                            final Class<? extends Packet> packet = PacketRegistry.get(packetId);
                            final UUID connectionUUID = readingByteBuffer.readUUID();

                            if (packet.equals(SPacketRequestExchange.class) && !client.isServer()) {
                                client.getConnectionUUID().set(connectionUUID);
                            }

                            Packet thePacket = packet.getConstructor(UUID.class).newInstance(connectionUUID);

                            EventPacket.Receive event = new EventPacket.Receive(thePacket);

                            thePacket.receive(readingByteBuffer);

                            if(client.isServer())
                                ServerEventBus.EVENT_BUS.post(event);
                            else
                                ClientEventBus.EVENT_BUS.post(event);
                        } else {
                            socket.close();
                        }
                    }

                    if (client.isServer() && start[0] == 0) {
                        start[0] = System.currentTimeMillis();
                    }

                    if (((System.currentTimeMillis() - start[0]) > threshold) && client.isServer()) {
                        Client offender = ServerSocketAcceptingThread.getClient(client.getConnectionUUID().get());

                        if (offender == null) {
                            interrupt();
                            return;
                        }

                        ServerEventBus.EVENT_BUS.post(new EventClientDisconnect(offender));
                        interrupt();
                    }
                } catch (final InstantiationException | IllegalAccessException | NoSuchMethodException |
                               InvocationTargetException exception) {
                    exception.printStackTrace();
                } catch (final IOException ignored) {
                    interrupt();
                }
            }
        }, 0, 1);
    }

    /**
     * Stops the thread
     */
    public void interrupt() {
        try {
            this.finalInputStream.close();
            this.timer.cancel();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}
