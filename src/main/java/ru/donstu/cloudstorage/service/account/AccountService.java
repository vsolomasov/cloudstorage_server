package ru.donstu.cloudstorage.service.account;

import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервисов для {@link ru.donstu.cloudstorage.domain.account.entity.Account}
 *
 * @author v.solomasov
 */
public interface AccountService {

    /**
     * Сохранение аккаунта
     *
     * @param account
     */
    void saveAccount(Account account);

    /**
     * Обновить имя аккаунта
     *
     * @param account
     * @param name
     */
    void updateAccountName(Account account, String name);

    /**
     * Обновить электронную почту аккаунта
     *
     * @param account
     * @param email
     */
    void updateAccountEmail(Account account, String email);

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

    /**
     * Проверка на совпадение элекстронной почты
     *
     * @param email
     * @return
     */
    boolean checkAccountEmail(String email);
}
