package ru.donstu.cloudstorage.service.userfiles;

import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * Поиск всех файлов аккаунта
     *
     * @param account
     * @return
     */
    List<UserFiles> findUserFilesByAccount(Account account);

    /**
     * Проверка файла пользователя на совпадение
     *
     * @param account
     * @param fileName
     * @return
     */
    boolean checkUserFile(Account account, String fileName);

    /**
     * Удаление файла
     *
     * @param id
     * @param account
     */
    void deleteFile(Long id, Account account);

    /**
     * Удаление папки пользваотеля для хранения файлов
     *
     * @param id
     */
    void deleteFolder(Long id);

    /**
     * Скачивание файла c сервера
     *
     * @param id
     * @param account
     * @param response
     */
    void downloadFile(Long id, Account account, HttpServletResponse response);
}
