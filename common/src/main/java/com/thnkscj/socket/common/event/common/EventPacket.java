package com.thnkscj.socket.common.event.common;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.packet.Packet;

@SuppressWarnings("unused unchecked")
public class EventPacket extends Event {

    private final Packet packet;

    public EventPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public <T extends Packet> T getPacketCasted(){
        return (T) packet;
    }

    public <T extends Packet> boolean isPacket(Class<T> cls){
        return cls.isAssignableFrom(packet.getClass());
    }

    public static class Send extends EventPacket {

        public Send(Packet packet) {
            super(packet);
        }
    }

    public static class Receive extends EventPacket {

        public Receive(Packet packet) {
            super(packet);
        }
    }
}
