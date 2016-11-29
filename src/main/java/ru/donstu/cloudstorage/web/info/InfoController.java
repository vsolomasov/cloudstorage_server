package ru.donstu.cloudstorage.web.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.donstu.cloudstorage.service.news.NewsService;
import ru.donstu.cloudstorage.service.security.SecurityService;

import static ru.donstu.cloudstorage.web.cloud.CloudController.REDIRECT_CLOUD;
import static ru.donstu.cloudstorage.web.info.InfoController.ROUTE_BASE;

/**
 * Контроллер страницы информации
 *
 * @author v.solomasov
 */
@Controller
@RequestMapping(ROUTE_BASE)
public class InfoController {

    public static final String ROUTE_BASE = "/";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public String infoPage(Model model) {
        boolean isLogged = securityService.isLoggedUser();
        if (isLogged) {
            return REDIRECT_CLOUD;
        }
        model.addAttribute("news", newsService.findAllNews());
        model.addAttribute("isLogged", isLogged);
        return "info";
    }
}
