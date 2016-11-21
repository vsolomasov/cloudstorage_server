package ru.donstu.cloudstorage.domain.userfiles;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;

/**
 * @author v.solomasov
 */
public interface UserFilesRepository extends JpaRepository<UserFiles, Long> {
}
