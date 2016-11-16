package ru.donstu.cloudstorage.web.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер настройки аккаунта
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @RequestMapping(method = RequestMethod.GET)
    public String settingsPage(){
        return "settings";
    }
}
