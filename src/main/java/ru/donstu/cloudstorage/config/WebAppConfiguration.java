package ru.donstu.cloudstorage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static ru.donstu.cloudstorage.config.constant.Constants.PACKAGE_PROJECT;

/**
 * Конфигурация Web MVC
 *
 * @author v.solomasov
 */
@Configuration
@EnableWebMvc
@ComponentScan(PACKAGE_PROJECT)
public class WebAppConfiguration extends WebMvcConfigurerAdapter {
}
