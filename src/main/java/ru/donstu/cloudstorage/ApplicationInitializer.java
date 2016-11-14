package ru.donstu.cloudstorage;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.donstu.cloudstorage.config.SecurityConfiguration;
import ru.donstu.cloudstorage.config.WebAppConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import static ru.donstu.cloudstorage.config.constant.Constants.DISPATCHER;
import static ru.donstu.cloudstorage.config.constant.Constants.SERVLET_MAPPING;

/**
 * Регистрация контекста с конфигурацией
 *
 * @author v.solomasov
 */
@Order(1)
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebAppConfiguration.class);
        applicationContext.register(SecurityConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER, new DispatcherServlet(applicationContext));
        servletRegistration.addMapping(SERVLET_MAPPING);
        servletRegistration.setLoadOnStartup(1);
    }
}
