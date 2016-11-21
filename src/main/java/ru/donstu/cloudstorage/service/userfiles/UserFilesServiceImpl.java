package ru.donstu.cloudstorage.service.userfiles;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.userfiles.UserFilesRepository;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;

import java.io.*;
import java.util.Calendar;

/**
 * @author v.solomasov
 *
 * Реализация интерфейса {@link UserFilesService}
 */
@Service
public class UserFilesServiceImpl implements UserFilesService {

    private static final Logger logger = Logger.getLogger(UserFilesServiceImpl.class);

    @Autowired
    private UserFilesRepository filesRepository;

    @Override
    public void uploadFile(Account account, MultipartFile file) {
        String fullNameFile = file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            saveFileOnDisk(account.getName(), fullNameFile, bytes);
            saveFile(account, fullNameFile);
        } catch (IOException e) {
            logger.debug(String.format("Ошибка чтения загружаемого файла %s", fullNameFile));
        }
    }

    /**
     * Создание информации в базе о загруженном файле на сервер
     *
     * @param account
     * @param fileName
     */
    private void saveFile(Account account, String fileName){
        UserFiles userFiles = new UserFiles();
        userFiles.setAccount(account);
        userFiles.setFileName(fileName);
        userFiles.setDateUpload(Calendar.getInstance());
        filesRepository.save(userFiles);
    }

    /**
     * Метод загрузки файла на диск сервера
     *
     * @param userName
     * @param fileName
     * @param bytes
     */
    private void saveFileOnDisk(String userName, String fileName, byte[] bytes){
        try {
            String rootPath = "C:\\test";
            File dir = new File(rootPath + File.separator + userName);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
            outputStream.write(bytes);
            outputStream.close();
            logger.info(String.format("Файл %s загружен", fileName));
        } catch (FileNotFoundException e) {
            logger.info(String.format("Файл %s не найден", fileName));
        } catch (IOException e) {
            logger.info(String.format("Ошибка загрузки файла %s", fileName));
        }
    }
}
