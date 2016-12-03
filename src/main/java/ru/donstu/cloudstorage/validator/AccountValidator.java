package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.message.entity.Message;
import ru.donstu.cloudstorage.domain.message.enums.Type;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.validator.util.RegexUtil;

import java.util.List;

/**
 * Класс валидации всех полей {@link Account}
 *
 * @author v.solomasov
 */
@Component
public class AccountValidator {

    private static final String LOGIN_REGEX = "validator.login.reg";

    private static final String LOGIN_SAME = "validator.login.same";

    private static final String EMAIL_REGEX = "validator.email.reg";

    private static final String EMAIL_SAME = "validator.email.same";

    private static final String EMAIL_CURRENT = "validator.email.current";

    private static final String PASSWORD_REGEX = "validator.password.reg";

    private static final String PASSWORD_EQUALS = "validator.password.not_equals";

    private static final String PASSWORD_CURRENT = "validator.password.current";

    @Autowired
    private Environment environment;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Валидация {@link Account}
     *
     * @param account
     * @param confirmPassword
     * @param messages
     */
    public void validate(Account account, String confirmPassword, List<Message> messages) {
        validateName(account.getName(), messages);
        validateEmail(account.getEmail(), messages);
        validatePassword(account.getPassword(), confirmPassword, messages);
    }

    /**
     * Валидация имени(логина)
     *
     * @param name
     * @param messages
     */
    public void validateName(String name, List<Message> messages) {
        if (!RegexUtil.checkRegEx(name, RegexUtil.PATTERN_LOGIN_1) || !RegexUtil.checkRegEx(name, RegexUtil.PATTERN_LOGIN_2)) {
            messages.add(new Message(environment.getRequiredProperty(LOGIN_REGEX), Type.DANGER));
        }
        if (accountService.checkAccountName(name)) {
            messages.add(new Message(environment.getRequiredProperty(LOGIN_SAME), Type.DANGER));
        }
    }

    /**
     * Валидация электронной почты
     *
     * @param email
     * @param messages
     */
    public void validateEmail(String email, List<Message> messages) {
        if (!RegexUtil.checkRegEx(email, RegexUtil.PATTERN_EMAIL)) {
            messages.add(new Message(environment.getRequiredProperty(EMAIL_REGEX), Type.DANGER));
        }
        if (accountService.checkAccountEmail(email)) {
            messages.add(new Message(environment.getRequiredProperty(EMAIL_SAME), Type.DANGER));
        }
    }

    public void validateEmail(Account account, String currentEmail, String newEmail, List<Message> messages) {
        if (account.getEmail().equals(currentEmail)) {
            validateEmail(newEmail, messages);
        } else {
            messages.add(new Message(environment.getRequiredProperty(EMAIL_CURRENT), Type.DANGER));
        }
    }

    /**
     * Валидация пароля
     *
     * @param password
     * @param confirmPassword
     * @param messages
     */
    public void validatePassword(String password, String confirmPassword, List<Message> messages) {
        if (!RegexUtil.checkRegEx(password, RegexUtil.PATTERN_PASSWORD)) {
            messages.add(new Message(environment.getRequiredProperty(PASSWORD_REGEX), Type.DANGER));
        }
        if (!password.equals(confirmPassword)) {
            messages.add(new Message(environment.getRequiredProperty(PASSWORD_EQUALS), Type.DANGER));
        }
    }

    public void validatePassword(Account account, String currentPassword, String newPassword, String confirmPassword, List<Message> messages) {
        if (validateCurrentPassword(account, currentPassword, messages)) {
            validatePassword(newPassword, confirmPassword, messages);
        }
    }

    /*TODO: Как добавиться SHA, сравнивать хэш-функции*/
    public boolean validateCurrentPassword(Account account, String currentPassword, List<Message> messages) {
        if (passwordEncoder.matches(currentPassword, account.getPassword())) {
            return true;
        }
        messages.add(new Message(environment.getRequiredProperty(PASSWORD_CURRENT), Type.DANGER));
        return false;
    }
}
