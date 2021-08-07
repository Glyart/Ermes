package com.glyart.ermes.utils;

import com.glyart.ermes.common.messages.MessageSerializable;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.UUID;

public class ErmesDataOutput implements ByteArrayDataOutput {

    private final ByteArrayDataOutput output;

    public ErmesDataOutput() {
        this.output = ByteStreams.newDataOutput();
    }

    public ErmesDataOutput(ByteArrayDataOutput output) {
        Preconditions.checkNotNull(output);
        this.output = output;
    }

    @Override
    public void write(int value) {
        output.write(value);
    }

    @Override
    public void write(byte[] value) {
        output.write(value);
    }

    @Override
    public void write(byte[] value, int offset, int length) {
        output.write(value, offset, length);
    }

    @Override
    public void writeBoolean(boolean value) {
        output.writeBoolean(value);
    }

    @Override
    public void writeByte(int value) {
        output.writeByte(value);
    }

    @Override
    public void writeShort(int value) {
        output.writeShort(value);
    }

    @Override
    public void writeChar(int value) {
        output.writeShort(value);
    }

    public void writeShort(short value) {
        output.writeShort(value);
    }

    public void writeChar(char value) {
        output.writeChar(value);
    }

    @Override
    public void writeInt(int value) {
        output.writeInt(value);
    }

    @Override
    public void writeLong(long value) {
        output.writeLong(value);
    }

    @Override
    public void writeFloat(float value) {
        output.writeFloat(value);
    }
    @Override

    public void writeDouble(double value) {
        output.writeDouble(value);
    }

    @Override
    public void writeChars(String value) {
        output.writeChars(value);
    }

    @Override
    public void writeUTF(String value) {
        output.writeUTF(value);
    }

    @Override
    public void writeBytes(String value) {
        output.write(value.getBytes());
    }

    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public <T extends MessageSerializable> void writeSerializable(T serializable) {
        serializable.write(this);
    }

    public byte[] toByteArray() {
        return output.toByteArray();
    }
    
}
