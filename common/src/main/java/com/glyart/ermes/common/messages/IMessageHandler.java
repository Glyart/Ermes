package com.glyart.ermes.common.messages;

public interface IMessageHandler<T extends Message> {

    void handle(T message);

}
