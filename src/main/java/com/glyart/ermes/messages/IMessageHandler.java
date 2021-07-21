package com.glyart.ermes.messages;

public interface IMessageHandler<T extends Message> {

    void handle(T message);

}
