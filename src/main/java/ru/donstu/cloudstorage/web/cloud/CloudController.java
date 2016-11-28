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
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;
import ru.donstu.cloudstorage.validator.FileValidator;

import javax.servlet.http.HttpServletResponse;

import static ru.donstu.cloudstorage.web.cloud.CloudController.ROUTE_CLOUD;


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
                           @RequestParam(value = "errors", required = false) boolean errors) {
        Account account = securityService.getLoggedAccount();
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("userFiles", filesService.findUserFilesByAccount(account));
        model.addAttribute("errors", errors);
        return "cloud";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUserFile(@RequestParam("file") MultipartFile file,
                               Model model) {
        boolean valid = fileValidator.validate(file);
        if (!valid) {
            model.addAttribute("errors", true);
            return REDIRECT_CLOUD;
        }
        filesService.uploadFile(securityService.getLoggedAccount(), file);
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
