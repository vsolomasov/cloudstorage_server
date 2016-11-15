package ru.donstu.cloudstorage.web.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.validator.UserValidator;

/**
 * Контроллер регистрации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String registrationPage(Model model){
        model.addAttribute("accountForm", new Account());
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registartionForm(@ModelAttribute("accountForm") Account accountForm,
                               BindingResult bindingResult,
                               Model model) {
        userValidator.validate(accountForm, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        accountService.saveAccount(accountForm);
        return "redirect:/login";
    }
}
