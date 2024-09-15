package com.thnkscj.skyliner;

public interface EventHook<T> {

    void invoke(T event);
}
