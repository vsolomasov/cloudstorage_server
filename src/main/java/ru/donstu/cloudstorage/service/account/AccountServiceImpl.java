package ru.donstu.cloudstorage.service.account;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        account.setRole(Role.ROLE_USER);
        account.setDataCreate(Calendar.getInstance());
        logger.info(String.format("Зарегистрирован новый пользовательй %s", account.getName()));
        accountRepository.save(account);
    }

    @Override
    public void updateAccountName(Account account, String name) {
        logger.info(String.format("Пользователь %d сменил имя %s на %s", account.getId(), account.getName(), name));
        account.setName(name);
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByName(String name) {
        Account account = accountRepository.findByName(name);
        return account;
    }

    @Override
    public boolean checkAccountName(String name) {
        Account account = accountRepository.findByName(name);
        if (account == null) {
            logger.info(String.format("Пользователь с именем %s - не найден", name));
            return false;
        }
        logger.info(String.format("Пользователь с именем %s - найден (id = %d)", name, account.getId()));
        return true;
    }
}
