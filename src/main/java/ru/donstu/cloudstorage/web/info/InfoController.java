package ru.donstu.cloudstorage.web.info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер информации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping("/")
public class InfoController {

    @RequestMapping(method = RequestMethod.GET)
    public String infoPage(){
        return "info";
    }
}
