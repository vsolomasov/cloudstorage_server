package ru.donstu.cloudstorage.web.setting;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.service.security.SecurityService;
import ru.donstu.cloudstorage.validator.AccountValidator;
import ru.donstu.cloudstorage.validator.EmailValidator;
import ru.donstu.cloudstorage.validator.PasswordValidator;

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

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @RequestMapping(method = RequestMethod.GET)
    public String settingsPage(Model model) {
        model.addAttribute("isLogged", securityService.isLoggedUser());
        model.addAttribute("account", securityService.getLoggedAccount());
        return "settings";
    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public String settingsName(@RequestParam("name") String name,
                               Model model) {
        Account account = securityService.getLoggedAccount();
        if (accountService.checkAccountName(name)) {
            model.addAttribute("nameError", true);
            return "redirect:/settings";
        }
        accountService.updateAccountName(account, name);
        return "redirect:/cloud";
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String settingsEmail(@RequestParam("currentEmail") String currentEmail,
                                @RequestParam("newEmail") String newEmail,
                                Model model) {
        Account account = securityService.getLoggedAccount();
        if (!emailValidator.validate(account, currentEmail, newEmail)) {
            model.addAttribute("emailError", true);
            return "redirect:/settings";
        }
        accountService.updateAccountEmail(account, newEmail);
        return "redirect:/cloud";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String settingsPassword(@RequestParam("currentPassword") String currentPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("confirmPassword") String confirmPassword,
                                   Model model) {
        Account account = securityService.getLoggedAccount();
        if (!passwordValidator.validate(account, currentPassword, newPassword, confirmPassword)) {
            model.addAttribute("passwordError", true);
            return "redirect:/settings";
        }
        accountService.updateAccountPassword(account, newPassword, confirmPassword);
        return "redirect:/cloud";
    }
}
