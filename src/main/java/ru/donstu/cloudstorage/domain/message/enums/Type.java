package ru.donstu.cloudstorage.domain.message.enums;

/**
 * Типы сообщений
 *
 * @author v.solomasov
 */
public enum Type {

    ERROR,
    DANGER,
    SUCCESS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
