package com.thnkscj.socket.common.event;

/**
 * When the event happened.
 */
public enum Era {

    /**
     * Before something happened. e.g. before a message is sent. (Can be used to cancel the event)
     */
    PRE,

    /**
     * After something happened. e.g. after a message is sent.
     */
    POST
}
