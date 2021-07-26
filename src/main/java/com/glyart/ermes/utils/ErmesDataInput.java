package com.glyart.ermes.utils;

import com.glyart.ermes.messages.MessageSerializable;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class ErmesDataInput implements ByteArrayDataInput {

    private final ByteArrayDataInput input;

    public ErmesDataInput(byte[] data) {
        this.input = ByteStreams.newDataInput(data);
    }

    public ErmesDataInput(ByteArrayDataInput input) {
        Preconditions.checkNotNull(input);
        this.input = input;
    }

    @Override
    public void readFully(byte[] buffer) {
        input.readFully(buffer);
    }

    @Override
    public void readFully(byte[] buffer, int offset, int length) {
        input.readFully(buffer, offset, length);
    }

    @Override
    public int skipBytes(int n) {
        return input.skipBytes(n);
    }

    @Override
    public boolean readBoolean() {
        return input.readBoolean();
    }

    @Override
    public byte readByte() {
        return input.readByte();
    }

    @Override
    public int readUnsignedByte() {
        return input.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return input.readShort();
    }

    @Override
    public int readUnsignedShort() {
        return input.readUnsignedShort();
    }

    @Override
    public char readChar() {
        return input.readChar();
    }

    @Override
    public int readInt() {
        return input.readInt();
    }

    @Override
    public long readLong() {
        return input.readLong();
    }

    @Override
    public float readFloat() {
        return input.readFloat();
    }

    @Override
    public double readDouble() {
        return input.readDouble();
    }

    @Override
    public String readLine() {
        return input.readLine();
    }

    @Override
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
