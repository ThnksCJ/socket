package com.thnkscj.socket.common.event.common;

import com.thnkscj.socket.common.event.Event;
import com.thnkscj.socket.common.packet.Packet;

/**
 * Gets fired on packet based actions
 */
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

    /**
     * Checks if the packet is an instance of the given class
     *
     * @param cls The class to check
     *
     * @return True if the packet is an instance of the given class
     */
    public <T extends Packet> boolean isPacket(Class<T> cls){
        return cls.isAssignableFrom(packet.getClass());
    }

    /**
     * Called when a packet is sent
     */
    public static class Send extends EventPacket {

        public Send(Packet packet) {
            super(packet);
        }
    }

    /**
     * Called when a packet is received
     */
    public static class Receive extends EventPacket {

        public Receive(Packet packet) {
            super(packet);
        }
    }
}
