package com.thnkscj.socket.common.util.bytes;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.IntStream;

public class ReadingByteBuffer {
    private final ByteBuffer byteBuffer;

    public ReadingByteBuffer(final byte... bytes) {
        this.byteBuffer = ByteBuffer.wrap(bytes);
    }

    public boolean readBoolean() {
        return this.readByte() == 1;
    }

    public byte readByte() {
        return this.byteBuffer.get();
    }

    public short readShort() {
        return this.byteBuffer.getShort();
    }

    public int readInt() {
        return this.byteBuffer.getInt();
    }

    public long readLong() {
        return this.byteBuffer.getLong();
    }

    public float readFloat() {
        return this.byteBuffer.getFloat();
    }

    public double readDouble() {
        return this.byteBuffer.getDouble();
    }

    public char readChar() {
        return (char) this.readByte();
    }

    public String[] readStringArray() {
        final int length = this.readInt();
        final String[] strings = new String[length];

        IntStream.range(0, length).forEach(i -> strings[i] = this.readString());

        return strings;
    }

    public String readString() {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        IntStream.range(0, length).forEach(i -> bytes[i] = this.readByte());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] readBytes() {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        IntStream.range(0, length).forEach(i -> bytes[i] = this.readByte());
        return bytes;
    }

    public UUID readUUID() {
        return UUID.fromString(this.readString());
    }
}