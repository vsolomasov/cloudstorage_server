package ru.donstu.cloudstorage.web.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.message.entity.Message;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.validator.AccountValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ru.donstu.cloudstorage.web.cloud.CloudController.REDIRECT_CLOUD;
import static ru.donstu.cloudstorage.web.login.LoginController.REDIRECT_LOGIN;
import static ru.donstu.cloudstorage.web.registration.RegistrationController.ROUTE_REGISTRATION;

/**
 * Контроллер страницы регистрации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping(ROUTE_REGISTRATION)
public class RegistrationController {

    public static final String ROUTE_REGISTRATION = "/registration";

    public static final String REDIRECT_REGISTRATION = "redirect:" + ROUTE_REGISTRATION;

    public static final String MESSAGES = "messages";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String registrationPage(HttpServletRequest request,
                                   Model model) {
        boolean isLogged = securityService.isLoggedUser();
        if (isLogged) {
            return REDIRECT_CLOUD;
        }
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("accountForm", new Account());
        model.addAttribute(MESSAGES, request.getSession().getAttribute(MESSAGES));
        request.getSession().removeAttribute(MESSAGES);
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registrationForm(@ModelAttribute("accountForm") Account accountForm,
                                   HttpServletRequest request,
                                   Model model) {
        List<Message> messages = new ArrayList<>();
        accountValidator.validate(accountForm, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_REGISTRATION;
        }
        accountService.saveAccount(accountForm);
        return REDIRECT_LOGIN;
    }
}
