package ru.donstu.cloudstorage.domain.userfiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;

import java.util.List;

/**
 * @author v.solomasov
 */
public interface UserFilesRepository extends JpaRepository<UserFiles, Long> {

    List<UserFiles> findByAccount(Account account);

    UserFiles findByAccountAndFileName(Account account, String fileName);

    UserFiles findByIdAndAccount(Long id, Account account);
}
