package com.glyart.ermes.rabbitmq;

import com.glyart.ermes.common.channels.IDataCompressor;
import com.glyart.ermes.common.connections.IConnection;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnection implements IConnection<RabbitMQMessagingChannel, Connection> {

    private final RabbitMQCredentials credentials;
    private Connection connection;

    private RabbitMQConnection(RabbitMQCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(credentials.getHostname());
        factory.setUsername(credentials.getUsername());
        factory.setPassword(credentials.getPassword());
        factory.setPort(credentials.getPort());

        connection = factory.newConnection();
    }

    @Override
    public void disconnect() throws IOException {
        connection.close();
    }

    @Override
    public RabbitMQMessagingChannel createChannel(String name, IDataCompressor compressor, boolean canConsumeMessages, int compressionThreshold) throws Exception {
        RabbitMQMessagingChannel channel = new RabbitMQMessagingChannel(name, compressor, canConsumeMessages, compressionThreshold);
        channel.connect(this);
        return channel;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public static RabbitMQConnection create(RabbitMQCredentials credentials) {
        return new RabbitMQConnection(credentials);
    }

}
