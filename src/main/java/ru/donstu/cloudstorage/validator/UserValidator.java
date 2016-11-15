package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;

/**
 * Валидатор для {@link org.springframework.security.core.userdetails.User}
 * реализует интерфейс {@link Validator}
 *
 * @author v.solomasov
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name обязателен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email обязателен");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password обязателен");
    }
}
