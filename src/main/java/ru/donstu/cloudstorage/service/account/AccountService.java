package ru.donstu.cloudstorage.service.account;

import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервисов для {@link ru.donstu.cloudstorage.domain.account.entity.Account}
 *
 * @author v.solomasov
 */
public interface AccountService {

    /**
     * Сохранение аккаунты
     *
     * @param account
     */
    void saveAccount(Account account);

    /**
     * Поиск аккаунта по имени
     *
     * @param name
     * @return
     */
    Account findAccountByName(String name);

    /**
     * Проверка на совпадение имени
     *
     * @param name
     * @return
     */
    boolean checkAccountName(String name);
}
