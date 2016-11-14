package ru.donstu.cloudstorage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Тестовый контроллер
 *
 * @author v.solomasov
 */
@Controller
public class TestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(){
        return "test";
    }
}
