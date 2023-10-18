package com.thnkscj.socket.common.util.bytes;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

public class WritingByteBuf {

    private byte[] buffer;

    private int size;

    public WritingByteBuf() {
        this.buffer = new byte[16];
        this.size = 0;
    }

    public void writeBoolean(final boolean value) {
        writeByte(value ? (byte) 1 : 0);
    }

    public void writeByte(final byte value) {
        writeRaw(new byte[]{value});
    }

    public void writeShort(final short value) {
        put(2, b -> b.putShort(value));
    }

    public void writeInt(final int value) {
        put(4, b -> b.putInt(value));
    }

    public void writeLong(final long value) {
        put(8, b -> b.putLong(value));
    }

    public void writeFloat(final float value) {
        put(4, b -> b.putFloat(value));
    }

    public void writeDouble(final float value) {
        put(8, b -> b.putDouble(value));
    }

    public void writeChar(final char value) {
        writeByte((byte) value);
    }

    public void writeString(final String value) {
        if (checkVal(value)) return;

        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeBytes(bytes);
    }

    public void writeStringArray(final String[] value) {
        if (checkVal(value)) return;

        writeInt(value.length);
        for (final String s : value)
            writeString(s);
    }

    public void writeBytes(final byte[] value) {
        if (checkVal(value)) return;

        writeInt(value.length);
        writeRaw(value);
    }

    public void writeUUID(final UUID value) {
        if (checkVal(value)) return;

        writeString(value.toString());
    }

    public byte[] toBytes() {
        return Arrays.copyOf(buffer, buffer.length);
    }

    /**
     * Internal methods
     */

    private void put(int len, Consumer<ByteBuffer> consumer) {
        writeRaw(makeBytes(len, consumer));
    }

    private byte[] makeBytes(int len, Consumer<ByteBuffer> consumer) {
        ByteBuffer buf = ByteBuffer.allocate(len);
        consumer.accept(buf);
        buf.flip();
        return buf.array();
    }

    private void writeRaw(final byte[] value) {
        if (size + value.length >= buffer.length - 1) {
            int grow1 = (int) Math.ceil(buffer.length * 1.5);
            int grow2 = buffer.length + value.length + 8;
            byte[] newBuffer = new byte[Math.max(grow1, grow2)];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        System.arraycopy(value, 0, buffer, size, value.length);
        size += value.length;
    }

    private static boolean checkVal(Object val) {
        return val == null;
    }
}
