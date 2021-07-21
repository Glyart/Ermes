package com.glyart.ermes.channels;

public interface IDataCompressor {

    /**
     * This method compresses the array of bytes.
     * @param data The array of bytes to compress
     * @return The compressed array of bytes
     */
    byte[] compress(byte[] data);

    /**
     * This method decompresses the array of bytes
     * @param data The array of bytes to decompress
     * @return The decompressed array of bytes
     */
    byte[] decompress(byte[] data);

}
