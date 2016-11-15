package ru.donstu.cloudstorage.domain.account.enums;

/**
 * Перечесление ролей пользователей
 *
 * @author v.solomasov
 */
public enum Role {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
