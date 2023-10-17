package com.thnkscj.socket.common.util.bytes;

import org.boon.primitive.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A class that allows you to write to a {@link ByteBuf}.
 */
@SuppressWarnings("unused")
public class WritingByteBuffer {
    /**
     * The {@link ByteBuf} that is being written to.
     */
    private final ByteBuf byteBuf;

    /**
     * Creates a new {@link WritingByteBuffer} with the given {@link ByteBuf}.
     */
    public WritingByteBuffer() {
        this.byteBuf = ByteBuf.create(0);
    }

    /**
     * Checks if the given object is null.
     */
    private static boolean isNull(final Object input) {
        return input == null;
    }

    /**
     * Writes a boolean to the {@link ByteBuf}.
     *
     * @param value A boolean.
     */
    public void writeBoolean(final boolean value) {
        byteBuf.addByte(value ? 1 : 0);
    }

    /**
     * Writes a byte to the {@link ByteBuf}.
     *
     * @param value A byte.
     */
    public void writeByte(final byte value) {
        byteBuf.add(value);
    }

    /**
     * Writes a short to the {@link ByteBuf}.
     *
     * @param value A short.
     */
    public void writeShort(final short value) {
        byteBuf.add(value);
    }

    /**
     * Writes an int to the {@link ByteBuf}.
     *
     * @param value An int.
     */
    public void writeInt(final int value) {
        byteBuf.add(value);
    }

    /**
     * Writes a long to the {@link ByteBuf}.
     *
     * @param value A long.
     */
    public void writeLong(final long value) {
        byteBuf.add(value);
    }

    /**
     * Writes a float to the {@link ByteBuf}.
     *
     * @param value A float.
     */
    public void writeFloat(final float value) {
        byteBuf.add(value);
    }

    /**
     * Writes a double to the {@link ByteBuf}.
     *
     * @param value A double.
     */
    public void writeDouble(final double value) {
        byteBuf.add(value);
    }

    /**
     * Writes a char to the {@link ByteBuf}.
     *
     * @param value A char.
     */
    public void writeChar(final char value) {
        writeByte((byte) value);
    }

    /**
     * Writes a {@link ByteBuffer} to the {@link ByteBuf}.
     *
     * @param value A {@link ByteBuffer}.
     */
    public void writeString(final String value) {
        if (isNull(value)) return;

        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeBytes(bytes);
    }

    /**
     * Writes a {@link ByteBuffer} to the {@link ByteBuf}.
     *
     * @param value A {@link ByteBuffer}.
     */
    public void writeStringArray(final String[] value) {
        if (isNull(value)) return;

        writeInt(value.length);
        for (final String s : value)
            writeString(s);
    }

    /**
     * Writes a {@link ByteBuffer} to the {@link ByteBuf}.
     *
     * @param value A {@link ByteBuffer}.
     */
    public void writeBytes(final byte[] value) {
        if (isNull(value)) return;

        writeInt(value.length);
        byteBuf.add(value);
    }

    /**
     * Writes a {@link ByteBuffer} to the {@link ByteBuf}.
     *
     * @param value A {@link ByteBuffer}.
     */
    public void writeUUID(final UUID value) {
        if (isNull(value)) return;

        writeString(value.toString());
    }

    /**
     * Transforms the {@link ByteBuf} into a byte array. This is the final step in the writing process.
     *
     * @return A byte array.
     */
    public byte[] toBytes() {
        return byteBuf.toBytes();
    }
}