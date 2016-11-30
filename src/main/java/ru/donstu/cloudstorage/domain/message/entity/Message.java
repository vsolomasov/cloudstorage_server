package ru.donstu.cloudstorage.domain.message.entity;

import ru.donstu.cloudstorage.domain.message.enums.Type;

/**
 * Объект сообщений
 *
 * @author v.solomasov
 */
public class Message {

    private final String text;
    private final Type type;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }
}
