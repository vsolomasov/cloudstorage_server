package ru.donstu.cloudstorage;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Инициализация Security
 *
 * @author v.solomasov
 */
@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
