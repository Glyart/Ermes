package com.glyart.ermes.utils;

import com.glyart.ermes.messages.MessageSerializable;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class ErmesDataInput {

    private final ByteArrayDataInput input;

    public ErmesDataInput(byte[] data) {
        this.input = ByteStreams.newDataInput(data);
        
    }

    public void readFully(byte[] buffer) {
        input.readFully(buffer);
    }

    public void readFully(byte[] buffer, int offset, int length) {
        input.readFully(buffer, offset, length);
    }

    public int skipBytes(int n) {
        return input.skipBytes(n);
    }

    public boolean readBoolean() {
        return input.readBoolean();
    }

    public byte readByte() {
        return input.readByte();
    }

    public int readUnsignedByte() {
        return input.readUnsignedByte();
    }

    public short readShort() {
        return input.readShort();
    }

    public int readUnsignedShort() {
        return input.readUnsignedShort();
    }

    public char readChar() {
        return input.readChar();
    }

    public int readInt() {
        return input.readInt();
    }

    public long readLong() {
        return input.readLong();
    }

    public float readFloat() {
        return input.readFloat();
    }

    public double readDouble() {
        return input.readDouble();
    }

    public String readLine() {
        return input.readLine();
    }

    public String readUTF() {
        return input.readUTF();
    }

    public UUID readUUID() {
        return new UUID(input.readLong(), input.readLong());
    }

    public <T extends MessageSerializable> T readSerializable(Class<T> clazz) {
        try {
            T serializable = clazz.getConstructor().newInstance();
            serializable.read(this);
            return serializable;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("An error occurred while reading a MessageSerializable", e);
        }
    }

}
