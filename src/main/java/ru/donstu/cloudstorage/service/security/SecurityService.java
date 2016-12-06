package ru.donstu.cloudstorage.service.security;

import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервисов безопасности
 *
 * @author v.solomasov
 */
public interface SecurityService {

    /**
     * Авторизован ли пользователь
     *
     * @return
     */
    boolean isLoggedUser();

    /**
     * Получить авторизованный аккаунт
     *
     * @return
     */
    Account getLoggedAccount();

    /**
     * Авторизация пользователя
     *
     * @param name
     * @param password
     */
    void autoLogin(String name, String password);

    /**
     * Шифрование AES
     *
     * @param bytes
     * @return
     */
    byte[] encryption(byte[] bytes);

    /**
     * Дешифрование AES
     *
     * @param bytes
     * @return
     */
    byte[] decryption(byte[] bytes);
}
