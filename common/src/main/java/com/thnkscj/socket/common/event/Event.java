package com.thnkscj.socket.common.event;

public class Event {

    private final Era era;

    private boolean cancelled;

    public Event() {
        this.era = null;
        this.cancelled = false;
    }

    public Event(Era era) {
        this.era = era;
        this.cancelled = false;
    }

    public Era getEra() {
        return era;
    }

    public boolean hasEra() {
        return era != null;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
