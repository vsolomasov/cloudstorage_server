package ru.donstu.cloudstorage.service.account;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.donstu.cloudstorage.domain.account.AccountRepository;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.account.enums.Role;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;

import java.util.Calendar;
import java.util.List;

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

    @Autowired
    private UserFilesService filesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
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
        securityService.autoLogin(account.getName(), account.getPassword());
    }

    @Override
    public void updateAccountEmail(Account account, String email) {
        logger.info(String.format("Пользователь %d сменил почту %s на %s", account.getId(), account.getEmail(), email));
        account.setEmail(email);
        accountRepository.save(account);
    }

    @Override
    public void updateAccountPassword(Account account, String newPassword, String confirmPassword) {
        logger.info(String.format("Пользователь %d сменил пароль %s на %s", account.getId(), account.getPassword(), newPassword));
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        List<UserFiles> files = filesService.findUserFilesByAccount(account);
        files.stream().forEach(file -> filesService.deleteFile(file.getId(), account));
        filesService.deleteFolder(account.getId());
        accountRepository.delete(account);
        logger.info(String.format("Пользователь id=%s удален", account.getId()));
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

    @Override
    public boolean checkAccountEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            logger.info(String.format("Пользователь с почтой %s - не найден", email));
            return false;
        }
        logger.info(String.format("Пользователь с почтой %s - найден (id = %d)", email, account.getId()));
        return true;
    }
}
