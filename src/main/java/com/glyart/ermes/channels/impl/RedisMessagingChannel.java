package com.glyart.ermes.channels.impl;

import com.glyart.ermes.channels.IDataCompressor;
import com.glyart.ermes.connections.impl.RedisConnection;
import com.google.common.base.Preconditions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Base64;

public class RedisMessagingChannel extends AbstractMessagingChannel<RedisConnection> {

    private Jedis jedis;
    private JedisPubSub listener;
    private Thread jedisThread;

    public RedisMessagingChannel(String name, IDataCompressor dataCompressor, boolean canConsumeMessages, int compressionThreshold) {
        super(name, dataCompressor, canConsumeMessages, compressionThreshold);
    }

    @Override
    public void connect(RedisConnection connection) {
        Preconditions.checkState(jedis == null, name + " channel is already connected.");

        jedis = connection.getConnection();
        Preconditions.checkNotNull(jedis, "The Redis connection is not established.");

        if (canConsumeMessages) {
            listener = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    if (!channel.equals(name))
                        return;

                    byte[] data = Base64.getDecoder().decode(channel);
                    handleMessages(data);
                }
            };

            jedisThread = new Thread(() -> jedis.subscribe(listener, name));
            jedisThread.setName("MessagingChannel-" + name);
            jedisThread.start();
        }
    }

    @Override
    public void disconnect() {
        Preconditions.checkNotNull(jedis, name + " channel is not connected.");
        jedis.close();
        listener.unsubscribe();
        jedisThread.stop();

        jedis = null;
        listener = null;
        jedisThread = null;
    }

    @Override
    protected void sendData(byte[] data) throws Exception {
        jedis.publish(name, Base64.getEncoder().encodeToString(data));
    }

}
