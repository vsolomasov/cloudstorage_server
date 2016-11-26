package ru.donstu.cloudstorage.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * @author v.solomasov
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByName(String name);

    Account findByEmail(String email);
}
