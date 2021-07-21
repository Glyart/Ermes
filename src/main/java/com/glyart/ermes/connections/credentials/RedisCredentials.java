package com.glyart.ermes.connections.credentials;

/**
 * This class represents the credentials to connect to a Redis server
 */
public class RedisCredentials {

    private final String hostname;
    private final String password;
    private final int port;

    private RedisCredentials(String hostname, String password, int port) {
        this.hostname = hostname;
        this.password = password;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public static final class RedisCredentialsBuilder {
        private String hostname;
        private String password;
        private int port;

        private RedisCredentialsBuilder() {
        }

        public static RedisCredentialsBuilder aRedisCredentials() {
            return new RedisCredentialsBuilder();
        }

        public RedisCredentialsBuilder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public RedisCredentialsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public RedisCredentialsBuilder withPort(int port) {
            this.port = port;
            return this;
        }

        public RedisCredentials build() {
            return new RedisCredentials(hostname, password, port);
        }
    }

}