package ru.donstu.cloudstorage.web.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;

/**
 * Контроллер основной страницы
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/cloud")
public class CloudController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserFilesService filesService;

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("isLogged", securityService.isLoggedUser());
        return "cloud";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUserFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()){
            return "redirect:/cloud";
        }
        filesService.uploadFile(securityService.getLoggedAccount(), file);
        return "redirect:/cloud";
    }
}
