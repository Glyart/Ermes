package com.glyart.ermes.connections;

import com.glyart.ermes.channels.IDataCompressor;
import com.glyart.ermes.channels.IMessagingChannel;

public interface IConnection<T extends IMessagingChannel<?>, V> {

    /**
     * Connects the IConnection
     */
    void connect() throws Exception;

    /**
     * Disconnects the IConnection
     */
    void disconnect() throws Exception;

    /**
     * Creates a new IMessagingChannel for this IConnection
     * @param name The name of the IMessagingChannel
     * @param compressor The IDataCompressor to use
     * @param canConsumeMessages True if it can handle messages
     * @param compressionThreshold The compression threshold
     * @return A new instance of IMessagingChannel
     */
    T createChannel(String name, IDataCompressor compressor, boolean canConsumeMessages, int compressionThreshold) throws Exception;

    /**
     * @return Returns the specific Connection used by the implementation
     */
    V getConnection();

}
