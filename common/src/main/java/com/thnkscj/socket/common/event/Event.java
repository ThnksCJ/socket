package com.thnkscj.socket.common.event;

/**
 * Used for the event system. All events should extend this class.
 */
public class Event {

    /**
     * The era of the event. (When it was called)
     */
    private final Era era;

    /**
     * Whether the event is cancelled.
     */
    private boolean cancelled;

    /**
     * Creates a new event with no era.
     */
    public Event() {
        this.era = null;
        this.cancelled = false;
    }

    /**
     * Creates a new event with the given era.
     *
     * @param era The era of the event.
     */
    public Event(Era era) {
        this.era = era;
        this.cancelled = false;
    }

    /**
     * Gets the era of the event.
     *
     * @return The era of the event.
     */
    public Era getEra() {
        return era;
    }

    /**
     * Checks if the event has an era.
     *
     * @return Whether the event has an era.
     */
    public boolean hasEra() {
        return era != null;
    }

    /**
     * Checks if the event is cancelled.
     *
     * @return Whether the event is cancelled.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets whether the event is cancelled.
     *
     * @param cancelled Whether the event is cancelled.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
