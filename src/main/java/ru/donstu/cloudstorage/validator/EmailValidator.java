package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;

/**
 * Валидация email
 *
 * @author v.solomasov
 */
@Component
public class EmailValidator {

    @Autowired
    private AccountService accountService;

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
}
