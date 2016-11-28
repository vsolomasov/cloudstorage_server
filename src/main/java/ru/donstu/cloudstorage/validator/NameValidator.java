package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.validator.util.RegexUtil;

import static ru.donstu.cloudstorage.config.constant.Constants.MESSAGE_PROPERTY;

/**
 * Валидация имени (логина)
 *
 * @author vyacheslafka
 */
@Component
@PropertySource(MESSAGE_PROPERTY)
public class NameValidator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment environment;

    public boolean validate(String name) {
        if (!RegexUtil.checkRegEx(name, RegexUtil.PATTERN_LOGIN)) {
            return false;
        }
        if (accountService.checkAccountName(name)) {
            return false;
        }
        return true;
    }

    public void validate(String name, Errors errors) {
        if (!RegexUtil.checkRegEx(name, RegexUtil.PATTERN_LOGIN)) {
            errors.reject(environment.getRequiredProperty("validator.login.reg"));
        }
        if (accountService.checkAccountName(name)) {
            errors.reject(environment.getRequiredProperty("validator.login.same"));
        }
    }
}
