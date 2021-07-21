package com.glyart.ermes.channels.impl;

import com.glyart.ermes.channels.IDataCompressor;
import com.glyart.ermes.channels.IMessagingChannel;
import com.glyart.ermes.connections.IConnection;
import com.glyart.ermes.messages.*;
import com.glyart.ermes.utils.ErmesDataInput;
import com.glyart.ermes.utils.ErmesDataOutput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMessagingChannel<T extends IConnection<? extends IMessagingChannel<?>, ?>> implements IMessagingChannel<T> {

    protected final Logger logger;
    protected final String name;
    protected final IDataCompressor dataCompressor;
    protected final boolean canConsumeMessages;
    protected final int compressionThreshold;
    protected final Map<Class<? extends Message>, List<IMessageHandler<? extends Message>>> messageHandlers = new HashMap<>();

    protected AbstractMessagingChannel(String name, IDataCompressor dataCompressor, boolean canConsumeMessages, int compressionThreshold) {
        this.logger = Logger.getLogger("MessagingChannel-" + name);
        this.name = name;
        this.dataCompressor = dataCompressor;
        this.canConsumeMessages = canConsumeMessages;
        this.compressionThreshold = compressionThreshold;
    }

    @Override
    public void sendMessages(Message... messages) throws Exception {
        ErmesDataOutput output = new ErmesDataOutput();
        for (Message message : messages) {
            try {
                output.writeInt(MessageRegistry.getMessageID(message.getClass()));
                message.write(output);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error occurred while writing a message: " + message.getClass().getSimpleName(), e);
            }
        }

        byte[] data = output.toByteArray();
        if (data.length > compressionThreshold) {
            sendData(dataCompressor.compress(data));
            return;
        }

        ByteArrayDataOutput finalOutput = ByteStreams.newDataOutput();
        finalOutput.writeInt(0);
        finalOutput.write(data);

        sendData(finalOutput.toByteArray());
    }

    @Override
    public <V extends Message> void registerHandler(Class<V> messageClazz, IMessageHandler<V> handler) {
        messageHandlers.computeIfAbsent(messageClazz, c -> new ArrayList<>()).add(handler);
    }

    protected void handleMessages(byte[] data) {
        ErmesDataInput input = new ErmesDataInput(dataCompressor.decompress(data));
        boolean readMessages = true;
        while (readMessages) {
            try {
                Message message = MessageRegistry.createMessage(input);
                handleMessage(message);
            } catch (Exception e) {
                if (e instanceof EOFException || (e instanceof IllegalStateException && e.getMessage().contains("EOFException")))
                    readMessages = false;
                else
                    logger.log(Level.WARNING, "An error occurred while reading messages", e);
            }
        }
    }

    /**
     * Send an array of bytes to this IMessageChannel
     * @param data The array of bytes to send
     */
    protected abstract void sendData(byte[] data) throws Exception;

    /**
     * This method calls the IMessageHandler(s) of a Message
     * @param message The received Message
     */
    private <V extends Message> void handleMessage(V message) {
        Class<? extends Message> clazz = message.getClass();
        if (!messageHandlers.containsKey(clazz))
            return;

        List<IMessageHandler<? extends Message>> handlers = messageHandlers.get(clazz);
        handlers.forEach(handler -> ((IMessageHandler<V>) handler).handle(message));
    }

}
