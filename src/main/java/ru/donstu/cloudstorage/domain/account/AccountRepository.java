package ru.donstu.cloudstorage.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * @author vyacheslafka
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
