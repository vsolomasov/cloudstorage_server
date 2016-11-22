package ru.donstu.cloudstorage.service.userfiles;

import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;

/**
 * Класс сервиса для работы с файлами пользователей
 *
 * @author v.solomasov
 */
public interface UserFilesService {

    /**
     * Загрузка файла на сервер
     *
     * @param account
     * @param file
     */
    void uploadFile(Account account, MultipartFile file);
}
