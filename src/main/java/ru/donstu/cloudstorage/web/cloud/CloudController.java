package ru.donstu.cloudstorage.web.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.security.SecurityService;

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

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("name", securityService.getLoggedAccount().getName());
        return "cloud";
    }
}
