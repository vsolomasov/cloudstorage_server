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
    private NameValidator nameValidator;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

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

        nameValidator.validate(account.getName(), errors);
        emailValidator.validate(account.getEmail(), errors);
        passwordValidator.validate(account.getPassword(), account.getConfirmPassword(), errors);
    }
}
