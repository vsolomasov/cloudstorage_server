package ru.donstu.cloudstorage.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.donstu.cloudstorage.domain.account.AccountRepository;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.account.enums.Role;

import java.util.Calendar;

/**
 * Реализация интерфейса {@link AccountService}
 *
 * @author v.solomasov
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        account.setRole(Role.ROLE_USER);
        account.setDataCreate(Calendar.getInstance());
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByName(String name) {
        Account account = accountRepository.findByName(name);
        return account;
    }
}
