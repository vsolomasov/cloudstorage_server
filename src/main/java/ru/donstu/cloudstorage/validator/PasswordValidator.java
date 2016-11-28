package ru.donstu.cloudstorage.validator;

import org.springframework.stereotype.Component;
import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Валидация пароля
 *
 * @author v.solomasov
 */
@Component
public class PasswordValidator {

    public boolean validate(Account account, String currentPassword, String newPassword, String confirmPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
        /*TODO: Как добавиться SHA, сравнивать хэш-функции*/
        if (!currentPassword.equals(account.getPassword())) {
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }
        if (!RegexUtil.checkRegEx(newPassword, RegexUtil.PATTERN_PASSWORD)) {
            return false;
        }
        return true;
    }
}
