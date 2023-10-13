package com.thnkscj.socket.common.util.bytes;

import org.boon.primitive.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class WritingByteBuffer {
    private final ByteBuf byteBuf;

    public WritingByteBuffer() {
        this.byteBuf = ByteBuf.create(0);
    }

    private static boolean isNull(final Object input) {
        return input == null;
    }

    public void writeBoolean(final boolean value) {
        byteBuf.addByte(value ? 1 : 0);
    }

    public void writeByte(final byte value) {
        byteBuf.add(value);
    }

    public void writeShort(final short value) {
        byteBuf.add(value);
    }

    public void writeInt(final int value) {
        byteBuf.add(value);
    }

    public void writeLong(final long value) {
        byteBuf.add(value);
    }

    public void writeFloat(final float value) {
        byteBuf.add(value);
    }

    public void writeDouble(final double value) {
        byteBuf.add(value);
    }

    public void writeChar(final char value) {
        writeByte((byte) value);
    }

    public void writeString(final String value) {
        if (isNull(value)) return;

        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeBytes(bytes);
    }

    public void writeStringArray(final String[] value) {
        if (isNull(value)) return;

        writeInt(value.length);
        for (final String s : value)
            writeString(s);
    }

    public void writeBytes(final byte[] bytes) {
        if (isNull(bytes)) return;

        writeInt(bytes.length);
        byteBuf.add(bytes);
    }

    public void writeUUID(final UUID value) {
        if (isNull(value)) return;

        writeString(value.toString());
    }

    public byte[] toBytes() {
        return byteBuf.toBytes();
    }
}