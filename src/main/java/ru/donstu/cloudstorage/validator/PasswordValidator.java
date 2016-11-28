package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.validator.util.RegexUtil;

/**
 * Валидация пароля
 *
 * @author v.solomasov
 */
@Component
public class PasswordValidator {

    /*TODO: Как добавиться SHA, сравнивать хэш-функции*/

    @Autowired
    private Environment environment;

    public boolean validate(Account account, String currentPassword, String newPassword, String confirmPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
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

    public void validate(String password, String confirmPassword, Errors errors) {
        if (!RegexUtil.checkRegEx(password, RegexUtil.PATTERN_PASSWORD)) {
            errors.reject(environment.getRequiredProperty("validator.password.reg"));
        }
        if (!password.equals(confirmPassword)) {
            errors.reject(environment.getRequiredProperty("validator.password.not_equals"));
        }
    }
}
