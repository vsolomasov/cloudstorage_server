package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;

/**
 * Валидация {@link org.springframework.web.multipart.MultipartFile}
 *
 * @author v.solomasov
 */
@Component
public class FileValidator {

    @Autowired
    private UserFilesService filesService;

    @Autowired
    private SecurityService securityService;

    public boolean validate(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        if (filesService.checkUserFile(securityService.getLoggedAccount(), file.getOriginalFilename())) {
            return false;
        }
        return true;
    }
}
