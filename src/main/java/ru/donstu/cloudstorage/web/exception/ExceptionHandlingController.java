package ru.donstu.cloudstorage.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

    private static final String NOT_FOUND = "Страницы %s - не существует";

    private static final String INTERNAL_SERVER_ERROR = "Параметр %s - неправильный";

    private static final String BAD_REQUEST = "Неправильный запрос - %s";

    @Autowired
    private SecurityService securityService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleError400(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("error", String.format(BAD_REQUEST, request.getRequestURI()));
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = getModelAndView();
        String requestUri = request.getRequestURI();
        if (requestUri != null) {
            modelAndView.addObject("error", String.format(NOT_FOUND, requestUri));
        }
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleError500(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = getModelAndView();
        String parameter = request.getQueryString();
        if (parameter != null) {
            modelAndView.addObject("error", String.format(INTERNAL_SERVER_ERROR, parameter));
        }
        return modelAndView;
    }

    private ModelAndView getModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ROUTE_ERROR);
        modelAndView.addObject("isLogged", securityService.isLoggedUser());
        return modelAndView;
    }
}
