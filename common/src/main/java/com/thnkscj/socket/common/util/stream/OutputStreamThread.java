package com.thnkscj.socket.common.util.stream;

import com.thnkscj.socket.common.client.Client;
import com.thnkscj.socket.common.client.ClientEventBus;
import com.thnkscj.socket.common.event.client.EventServerDisconnect;
import com.thnkscj.socket.common.event.common.EventPacket;
import com.thnkscj.socket.common.packet.Packet;
import com.thnkscj.socket.common.packet.PacketRegistry;
import com.thnkscj.socket.common.server.ServerEventBus;
import com.thnkscj.socket.common.util.bytes.WritingByteBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OutputStreamThread {

    private final Client client;
    private final Socket socket;
    private final List<Packet> packets = new ArrayList<>();
    private final Timer timer = new Timer();
    private OutputStream finalOutputStream;

    public OutputStreamThread(final Client client) {
        this.client = client;
        this.socket = this.client.getSocket();
    }

    public void run() throws IOException {
        finalOutputStream = this.socket.getOutputStream();

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (socket.isClosed()) {
                        interrupt();
                        return;
                    }

                    if (!packets.isEmpty()) {
                        final Packet packet = packets.get(0);

                        if (packet != null) {
                            packets.remove(0);
                            final WritingByteBuffer writingByteBuffer = new WritingByteBuffer();
                            final int packetId = PacketRegistry.indexOf(packet.getClass());

                            if (packetId == -1) {
                                return;
                            }

                            EventPacket.Send event = new EventPacket.Send(packet);

                            if (client.isServer())
                                ServerEventBus.EVENT_BUS.post(event);
                            else
                                ClientEventBus.EVENT_BUS.post(event);

                            writingByteBuffer.writeInt(packetId);
                            writingByteBuffer.writeUUID(client.getConnectionUUID().get());

                            packet.send(writingByteBuffer);

                            try {
                                final byte[] bytes = writingByteBuffer.toBytes();
                                assert finalOutputStream != null;

                                finalOutputStream.write(bytes.length);
                                finalOutputStream.write(bytes);
                                finalOutputStream.flush();
                            } catch (final SocketException exception) {
                                if(!client.isServer()) {
                                    ClientEventBus.EVENT_BUS.post(new EventServerDisconnect(exception.getMessage()));
                                    client.disconnect();
                                }
                            }
                        }
                    }
                } catch (final IOException | NullPointerException exception) {
                    exception.printStackTrace();
                }
            }
        }, 0, 1);
    }

    public void interrupt() {
        try {
            this.finalOutputStream.close();
            this.timer.cancel();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void send(final Packet packet) {
        this.packets.add(packet);
    }
}
