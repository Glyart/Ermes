package com.glyart.ermes.connections.credentials;

/**
 * This class represents the credentials to connect to a RabbitMQ server
 */
public class RabbitMQCredentials {

    private final String hostname;
    private final String username;
    private final String password;
    private final int port;

    private RabbitMQCredentials(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public static final class Builder {
        private String hostname;
        private String username;
        private String password;
        private int port;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        public RabbitMQCredentials build() {
            return new RabbitMQCredentials(hostname, username, password, port);
        }
    }

}
