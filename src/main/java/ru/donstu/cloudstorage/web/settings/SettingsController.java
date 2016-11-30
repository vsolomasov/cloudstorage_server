package ru.donstu.cloudstorage.web.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.message.entity.Message;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.validator.AccountValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ru.donstu.cloudstorage.config.constant.Constants.MESSAGE_PROPERTY;
import static ru.donstu.cloudstorage.web.cloud.CloudController.REDIRECT_CLOUD;
import static ru.donstu.cloudstorage.web.login.LoginController.REDIRECT_LOGOUT;
import static ru.donstu.cloudstorage.web.registration.RegistrationController.MESSAGES;
import static ru.donstu.cloudstorage.web.settings.SettingsController.ROUTE_SETTINGS;

/**
 * Контроллер страницы настроек аккаунта
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping(ROUTE_SETTINGS)
@PropertySource(MESSAGE_PROPERTY)
public class SettingsController {

    public static final String ROUTE_SETTINGS = "/settings";

    public static final String REDIRECT_SETTINGS = "redirect:" + ROUTE_SETTINGS;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String settingsPage(HttpServletRequest request,
                               Model model) {
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("account", securityService.getLoggedAccount());
        model.addAttribute(MESSAGES, request.getSession().getAttribute(MESSAGES));
        request.getSession().removeAttribute(MESSAGES);
        return "settings";
    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public String settingsName(@RequestParam("name") String name,
                               HttpServletRequest request) {
        List<Message> messages = new ArrayList();
        accountValidator.validateName(name, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_SETTINGS;
        }
        accountService.updateAccountName(securityService.getLoggedAccount(), name);
        return REDIRECT_CLOUD;
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String settingsEmail(@RequestParam("currentEmail") String currentEmail,
                                @RequestParam("newEmail") String newEmail,
                                HttpServletRequest request) {
        Account account = securityService.getLoggedAccount();
        List<Message> messages = new ArrayList();
        accountValidator.validateEmail(account, currentEmail, newEmail, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_SETTINGS;
        }
        accountService.updateAccountEmail(account, newEmail);
        return REDIRECT_CLOUD;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String settingsPassword(@RequestParam("currentPassword") String currentPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("confirmPassword") String confirmPassword,
                                   HttpServletRequest request) {
        Account account = securityService.getLoggedAccount();
        List<Message> messages = new ArrayList();
        accountValidator.validatePassword(account, currentPassword, newPassword, confirmPassword, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_SETTINGS;
        }
        accountService.updateAccountPassword(account, newPassword, confirmPassword);
        return REDIRECT_CLOUD;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String settingsDelete(@RequestParam("password") String password,
                                 HttpServletRequest request) {
        Account account = securityService.getLoggedAccount();
        List<Message> messages = new ArrayList();
        accountValidator.validateCurrentPassword(account, password, messages);
        if (!messages.isEmpty()) {
            request.getSession().setAttribute(MESSAGES, messages);
            return REDIRECT_SETTINGS;
        }
        accountService.deleteAccount(account);
        return REDIRECT_LOGOUT;
    }
}
