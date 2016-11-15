package ru.donstu.cloudstorage.service.account;

import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервисов для {@link ru.donstu.cloudstorage.domain.account.entity.Account}
 *
 * @author v.solomasov
 */
public interface AccountService {

    void saveAccount(Account account);

    Account findAccountByName(String name);
}
