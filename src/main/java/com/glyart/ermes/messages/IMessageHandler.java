package com.glyart.ermes.messages;

@FunctionalInterface
public interface IMessageHandler<T extends Message> {

    void handle(T message);

}
