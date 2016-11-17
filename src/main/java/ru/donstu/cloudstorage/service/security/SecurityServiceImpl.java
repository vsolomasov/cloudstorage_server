package ru.donstu.cloudstorage.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.account.enums.Role;
import ru.donstu.cloudstorage.service.account.AccountService;


/**
 * Реализация интерфейса {@link SecurityService}
 *
 * @author v.solomasov
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean isLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(Role.ROLE_USER));
    }

    @Override
    public Account getLoggedAccount() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            return accountService.findAccountByName(((UserDetails) userDetails).getUsername());
        }
        return null;
    }
}
