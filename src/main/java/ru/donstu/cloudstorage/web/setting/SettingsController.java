package ru.donstu.cloudstorage.web.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;
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

    @Autowired
    private AccountService accountService;

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
            model.addAttribute("nameError", "Errors name");
            return "redirect:/settings";
        }
        accountService.updateAccountName(account, name);
        return "redirect:/cloud";
    }
}
