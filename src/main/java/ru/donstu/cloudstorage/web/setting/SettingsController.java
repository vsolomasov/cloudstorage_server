package ru.donstu.cloudstorage.web.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.security.SecurityService;

/**
 * Контроллер настройки аккаунта
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public String settingsPage(Model model){
        model.addAttribute("isLogged", securityService.isLoggedUser());
        return "settings";
    }
}
