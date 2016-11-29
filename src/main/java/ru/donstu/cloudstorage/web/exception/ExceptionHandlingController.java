package ru.donstu.cloudstorage.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.donstu.cloudstorage.service.security.SecurityService;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер обработки исключений
 *
 * @author v.solomasov
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final String ROUTE_ERROR = "/error";

    private static final String PAGE_NOT_FOUND = "Страницы (%s), не существует";

    @Autowired
    private SecurityService securityService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView(ROUTE_ERROR);
        String requestUri = request.getRequestURI();
        if (request != null) {
            modelAndView.addObject("error", String.format(PAGE_NOT_FOUND, requestUri));
            modelAndView.addObject("isLogged", securityService.isLoggedUser());
        }
        return modelAndView;
    }
}
