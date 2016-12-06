package ru.donstu.cloudstorage.web.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.message.entity.Message;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;
import ru.donstu.cloudstorage.validator.FileValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static ru.donstu.cloudstorage.web.cloud.CloudController.ROUTE_CLOUD;
import static ru.donstu.cloudstorage.web.registration.RegistrationController.MESSAGES;


/**
 * Контроллер основной страницы
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping(ROUTE_CLOUD)
public class CloudController {

    public static final String ROUTE_CLOUD = "/cloud";

    public static final String REDIRECT_CLOUD = "redirect:" + ROUTE_CLOUD;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserFilesService filesService;

    @Autowired
    private FileValidator fileValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(Model model,
                           HttpServletRequest request) {
        Account account = securityService.getLoggedAccount();
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("userFiles", filesService.findUserFilesByAccount(account));
        model.addAttribute(MESSAGES, request.getSession().getAttribute(MESSAGES));
        request.getSession().removeAttribute(MESSAGES);
        return "cloud";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUserFile(@RequestParam("file") MultipartFile file,
                               HttpServletRequest request) {
        Account account = securityService.getLoggedAccount();
        List<Message> messages = new ArrayList();
        fileValidator.validate(account, file, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_CLOUD;
        }
        filesService.uploadFile(account, file);
        return REDIRECT_CLOUD;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUserFile(@PathVariable("id") Long id) {
        filesService.deleteFile(id, securityService.getLoggedAccount());
        return REDIRECT_CLOUD;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void downloadUserFIle(@PathVariable("id") Long id,
                                 HttpServletResponse response) {
        filesService.downloadFile(id, securityService.getLoggedAccount(), response);
    }
}
