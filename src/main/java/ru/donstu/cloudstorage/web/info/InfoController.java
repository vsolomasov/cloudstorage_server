package ru.donstu.cloudstorage.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.security.SecurityService;

/**
 * Контроллер информации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/")
public class InfoController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public String infoPage(Model model) {
        model.addAttribute("isLogged", securityService.isLoggedUser());
        return "info";
    }
}
