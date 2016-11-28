package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.validator.util.RegexUtil;

import static ru.donstu.cloudstorage.config.constant.Constants.MESSAGE_PROPERTY;

/**
 * Валидация email
 *
 * @author v.solomasov
 */
@Component
@PropertySource(MESSAGE_PROPERTY)
public class EmailValidator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment environment;

    public boolean validate(Account account, String currentEmail, String newEmail) {
        if (currentEmail.isEmpty() || newEmail.isEmpty()) {
            return false;
        }
        if (!currentEmail.equals(account.getEmail())) {
            return false;
        }
        if (!RegexUtil.checkRegEx(newEmail, RegexUtil.PATTERN_EMAIL)) {
            return false;
        }
        if (accountService.checkAccountEmail(newEmail)) {
            return false;
        }
        return true;
    }

    public void validate(String email, Errors errors) {
        if (!RegexUtil.checkRegEx(email, RegexUtil.PATTERN_EMAIL)) {
            errors.reject(environment.getRequiredProperty("validator.email.reg"));
        }
        if (accountService.checkAccountEmail(email)) {
            errors.reject(environment.getRequiredProperty("validator.email.same"));
        }
    }
}
