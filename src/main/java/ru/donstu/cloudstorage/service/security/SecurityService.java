package ru.donstu.cloudstorage.service.security;

/**
 * Класс сервисов для Security
 *
 * @author v.solomasov
 */
public interface SecurityService {

    String findLoggedInUseremail();

    void autoLogin(String email, String password);
}
