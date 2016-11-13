package ru.donstu.cloudstorage.config.constant;

import org.springframework.stereotype.Component;

/**
 * Константы необходимые для конфигурации проекта
 *
 * @author v.solomasov
 */
@Component
public class Constants {

    public final static String DISPATCHER = "dispatcher";

    public final static String SERVLET_MAPPING = "/";

    public final static String PACKAGE_PROJECT = "ru.donstu.cloudstorage";

    public final static String PACKAGE_ENTITY = PACKAGE_PROJECT + ".domain";

    public final static String PACKAGE_CONFIG = PACKAGE_PROJECT + ".config";

    public final static String RESOURCES_PROPERTY = "classpath:application.properties";
}
