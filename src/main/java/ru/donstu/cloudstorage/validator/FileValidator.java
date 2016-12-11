package ru.donstu.cloudstorage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.message.entity.Message;
import ru.donstu.cloudstorage.domain.message.enums.Type;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;

import java.util.List;

/**
 * Валидация {@link org.springframework.web.multipart.MultipartFile}
 *
 * @author v.solomasov
 */
@Component
public class FileValidator {

    private static final String FILE_EMPTY = "validator.file.empty";

    private static final String FILE_SAME = "validator.file.same";

    @Autowired
    private UserFilesService userFilesService;

    @Autowired
    private Environment environment;

    public void validate(Account account, MultipartFile multipartFile, List<Message> messages) {
        if (multipartFile.isEmpty()) {
            messages.add(new Message(environment.getRequiredProperty(FILE_EMPTY), Type.DANGER));
        }
        if (userFilesService.checkUserFile(account, multipartFile.getOriginalFilename())) {
            messages.add(new Message(environment.getRequiredProperty(FILE_SAME), Type.DANGER));
        }
    }
}
