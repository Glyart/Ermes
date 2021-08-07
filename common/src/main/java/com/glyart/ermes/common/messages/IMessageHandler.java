package com.glyart.ermes.common.messages;

@FunctionalInterface
public interface IMessageHandler<T extends Message> {

    void handle(T message);

}
