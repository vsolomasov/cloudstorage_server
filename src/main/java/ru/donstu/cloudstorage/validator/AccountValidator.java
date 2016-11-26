package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;

import static ru.donstu.cloudstorage.config.constant.Constants.MESSAGE_PROPERTY;

/**
 * Валидация {@link org.springframework.security.core.userdetails.User}
 * реализует интерфейс {@link Validator}
 *
 * @author v.solomasov
 */
@Component
@PropertySource(MESSAGE_PROPERTY)
public class AccountValidator implements Validator {

    @Autowired
    private Environment environment;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", environment.getRequiredProperty("validator.login.empty"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", environment.getRequiredProperty("validator.email.empty"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", environment.getRequiredProperty("validator.password.empty"));

        if (accountService.checkAccountName(account.getName())) {
            errors.reject(environment.getRequiredProperty("validator.login.same"));
        }

        if (!RegexUtil.checkRegEx(account.getName(), RegexUtil.PATTERN_LOGIN)) {
            errors.reject(environment.getRequiredProperty("validator.login.reg"));
        }

        if (!RegexUtil.checkRegEx(account.getEmail(), RegexUtil.PATTERN_EMAIL)) {
            errors.reject(environment.getRequiredProperty("validator.email.reg"));
        }

        if (accountService.checkAccountEmail(account.getEmail())) {
            errors.reject(environment.getRequiredProperty("validator.email.same"));
        }

        if (!RegexUtil.checkRegEx(account.getPassword(), RegexUtil.PATTERN_PASSWORD)) {
            errors.reject(environment.getRequiredProperty("validator.password.reg"));
        }

        if (!account.getPassword().equals(account.getConfirmPassword())) {
            errors.reject(environment.getRequiredProperty("validator.password.not_equals"));
        }
    }
}
