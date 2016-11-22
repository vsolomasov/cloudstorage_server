package ru.donstu.cloudstorage.service.userfiles;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.userfiles.UserFilesRepository;
import ru.donstu.cloudstorage.domain.userfiles.entity.UserFiles;

import java.io.*;
import java.util.Calendar;

import static ru.donstu.cloudstorage.config.constant.Constants.RESOURCES_PROPERTY;

/**
 * @author v.solomasov
 *
 * Реализация интерфейса {@link UserFilesService}
 */
@Service
@PropertySource(RESOURCES_PROPERTY)
public class UserFilesServiceImpl implements UserFilesService {

    private static final Logger logger = Logger.getLogger(UserFilesServiceImpl.class);

    @Autowired
    private UserFilesRepository filesRepository;

    @Autowired
    private Environment environment;

    @Override
    public void uploadFile(Account account, MultipartFile file) {
        String fullNameFile = file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            File dir = new File(environment.getRequiredProperty("win.user_files") + File.separator + account.getName());
            if (!dir.exists()){
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fullNameFile);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
            outputStream.write(bytes);
            outputStream.close();
            logger.info(String.format("Файл %s загружен", fullNameFile));
            saveInfoFile(account, fullNameFile);
        } catch (FileNotFoundException e) {
            logger.info(String.format("Файл %s не найден", fullNameFile));
        } catch (IOException e) {
            logger.info(String.format("Ошибка загрузки файла %s", fullNameFile));
        }
    }

    /**
     * Создание информации в базе о загруженном файле на сервер
     *
     * @param account
     * @param fileName
     */
    private void saveInfoFile(Account account, String fileName){
        UserFiles userFiles = new UserFiles();
        userFiles.setAccount(account);
        userFiles.setFileName(fileName);
        userFiles.setDateUpload(Calendar.getInstance());
        filesRepository.save(userFiles);
    }
}
