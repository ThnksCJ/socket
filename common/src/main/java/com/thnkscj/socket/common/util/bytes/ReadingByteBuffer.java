package com.thnkscj.socket.common.util.bytes;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * A wrapper for {@link ByteBuffer} that provides methods for reading data relative to {@link WritingByteBuffer}.
 */
@SuppressWarnings("unused")
public class ReadingByteBuffer {

    /**
     * The {@link ByteBuffer} that is being wrapped.
     */
    private final ByteBuffer byteBuffer;

    /**
     * Creates a new {@link ReadingByteBuffer} with the given {@link ByteBuffer}.
     *
     * @param bytes Used to create the {@link ByteBuffer}.
     */
    public ReadingByteBuffer(final byte... bytes) {
        this.byteBuffer = ByteBuffer.wrap(bytes);
    }

    /**
     * Reads a boolean from the {@link ByteBuffer}.
     * @return The boolean that was read.
     */
    public boolean readBoolean() {
        return this.readByte() == 1;
    }

    /**
     * Reads a byte from the {@link ByteBuffer}.
     * @return The byte that was read.
     */
    public byte readByte() {
        return this.byteBuffer.get();
    }

    /**
     * Reads a short from the {@link ByteBuffer}.
     * @return The short that was read.
     */
    public short readShort() {
        return this.byteBuffer.getShort();
    }

    /**
     * Reads an integer from the {@link ByteBuffer}.
     * @return The integer that was read.
     */
    public int readInt() {
        return this.byteBuffer.getInt();
    }

    /**
     * Reads a long from the {@link ByteBuffer}.
     * @return The long that was read.
     */
    public long readLong() {
        return this.byteBuffer.getLong();
    }

    /**
     * Reads a float from the {@link ByteBuffer}.
     * @return The float that was read.
     */
    public float readFloat() {
        return this.byteBuffer.getFloat();
    }

    /**
     * Reads a double from the {@link ByteBuffer}.
     * @return The double that was read.
     */
    public double readDouble() {
        return this.byteBuffer.getDouble();
    }

    /**
     * Reads a character from the {@link ByteBuffer}.
     * @return The character that was read.
     */
    public char readChar() {
        return (char) this.readByte();
    }

    /**
     * Reads a string array from the {@link ByteBuffer}.
     * @return The string array that was read.
     */
    public String[] readStringArray() {
        final int length = this.readInt();
        final String[] strings = new String[length];

        IntStream.range(0, length).forEach(i -> strings[i] = this.readString());

        return strings;
    }

    /**
     * Reads a byte array from the {@link ByteBuffer}.
     * @return The byte array that was read.
     */
    public String readString() {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        IntStream.range(0, length).forEach(i -> bytes[i] = this.readByte());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Reads a byte array from the {@link ByteBuffer}.
     * @return The byte array that was read.
     */
    public byte[] readBytes() {
        final int length = this.readInt();
        final byte[] bytes = new byte[length];
        IntStream.range(0, length).forEach(i -> bytes[i] = this.readByte());
        return bytes;
    }

    /**
     * Reads a {@link UUID} from the {@link ByteBuffer}.
     * @return The {@link UUID} that was read.
     */
    public UUID readUUID() {
        return UUID.fromString(this.readString());
    }
}