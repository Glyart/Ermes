package com.glyart.ermes.channels.impl;

import com.github.luben.zstd.Zstd;
import com.glyart.ermes.channels.IDataCompressor;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.Arrays;

public class ZSTDDataCompressor implements IDataCompressor {

    @Override
    public byte[] compress(byte[] data) {
        byte[] compressedData = Zstd.compress(data);
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeInt(data.length);
        output.writeInt(compressedData.length);
        output.write(compressedData);

        return output.toByteArray();
    }

    @Override
    public byte[] decompress(byte[] data) {
        ByteArrayDataInput input = ByteStreams.newDataInput(data);
        int realSize = input.readInt();
        if (realSize == 0)
            return Arrays.copyOfRange(data, 4, data.length);

        int compressedSize = input.readInt();
        byte[] dest = new byte[realSize];
        byte[] source = new byte[compressedSize];
        input.readFully(source);
        Zstd.decompress(dest, source);

        return dest;
    }

}
