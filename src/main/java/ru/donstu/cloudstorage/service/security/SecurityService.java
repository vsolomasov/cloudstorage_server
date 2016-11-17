package ru.donstu.cloudstorage.service.security;

import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервисов безопасности
 *
 * @author v.solomasov
 */
public interface SecurityService {

    boolean isLoggedUser();

    Account getLoggedAccount();
}
