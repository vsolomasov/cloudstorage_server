package ru.donstu.cloudstorage.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.security.SecurityService;

import static ru.donstu.cloudstorage.web.cloud.CloudController.REDIRECT_CLOUD;
import static ru.donstu.cloudstorage.web.login.LoginController.ROUTE_LOGIN;

/**
 * Контроллер страницы авторизации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping(ROUTE_LOGIN)
public class LoginController {

    public static final String ROUTE_LOGIN = "/login";

    public static final String REDIRECT_LOGIN = "redirect:" + ROUTE_LOGIN;

    public static final String REDIRECT_LOGOUT = "redirect:/logout";

    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage(Model model) {
        boolean isLogged = securityService.isLoggedUser();
        if (isLogged) {
            return REDIRECT_CLOUD;
        }
        model.addAttribute("isLogged", isLogged);
        return "login";
    }
}
