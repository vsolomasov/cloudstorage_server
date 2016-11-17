package ru.donstu.cloudstorage.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.security.SecurityService;

/**
 * Контроллер авторизации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(Model model) {
        boolean isLogged = securityService.isLoggedUser();
        if (isLogged) {
            return "redirect:/cloud";
        }
        model.addAttribute("isLogged", isLogged);
        return "login";
    }
}
