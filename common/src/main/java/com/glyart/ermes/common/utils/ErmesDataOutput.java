package com.glyart.ermes.common.utils;

import com.glyart.ermes.common.messages.MessageSerializable;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.UUID;

public class ErmesDataOutput  {

    private final ByteArrayDataOutput output;

    public ErmesDataOutput() {
        this.output = ByteStreams.newDataOutput();
    }

    public void write(int value) {
        output.write(value);
    }

    public void write(byte[] value) {
        output.write(value);
    }

    public void write(byte[] value, int offset, int length) {
        output.write(value, offset, length);
    }

    public void writeBoolean(boolean value) {
        output.writeBoolean(value);
    }

    public void writeByte(int value) {
        output.writeByte(value);
    }

    public void writeShort(short value) {
        output.writeShort(value);
    }

    public void writeChar(char value) {
        output.writeChar(value);
    }

    public void writeInt(int value) {
        output.writeInt(value);
    }

    public void writeLong(long value) {
        output.writeLong(value);
    }

    public void writeFloat(float value) {
        output.writeFloat(value);
    }

    public void writeDouble(double value) {
        output.writeDouble(value);
    }

    public void writeChars(String value) {
        output.writeChars(value);
    }

    public void writeUTF(String value) {
        output.writeUTF(value);
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
