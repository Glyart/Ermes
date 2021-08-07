package com.glyart.ermes.redis;

import com.glyart.ermes.common.channels.IDataCompressor;
import com.glyart.ermes.common.connections.IConnection;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisConnection implements IConnection<RedisMessagingChannel, JedisPool> {

    private final RedisCredentials credentials;
    private JedisPool pool;

    private RedisConnection(RedisCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void connect() {
        this.pool = new JedisPool(new JedisPoolConfig(), credentials.hostname(), credentials.port(), Protocol.DEFAULT_TIMEOUT, credentials.password());
    }

    @Override
    public void disconnect() {
        pool.close();
    }

    @Override
    public RedisMessagingChannel createChannel(String name, IDataCompressor compressor, boolean canConsumeMessages, int compressionThreshold) {
        RedisMessagingChannel channel = new RedisMessagingChannel(name, compressor, canConsumeMessages, compressionThreshold);
        channel.connect(this);
        return channel;
    }

    @Override
    public JedisPool getConnection() {
        return pool;
    }

    public static RedisConnection create(RedisCredentials credentials) {
        return new RedisConnection(credentials);
    }

}
