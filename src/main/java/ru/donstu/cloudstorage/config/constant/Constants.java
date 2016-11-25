package ru.donstu.cloudstorage.config.constant;

import org.springframework.stereotype.Component;

/**
 * Константы необходимые для конфигурации проекта
 *
 * @author v.solomasov
 */
@Component
public class Constants {

    public static final String DISPATCHER = "dispatcher";

    public static final String SERVLET_MAPPING = "/";

    public static final String PACKAGE_PROJECT = "ru.donstu.cloudstorage";

    public static final String PACKAGE_ENTITY = PACKAGE_PROJECT + ".domain";

    public static final String PACKAGE_CONFIG = PACKAGE_PROJECT + ".config";

    public static final String RESOURCES_PROPERTY = "classpath:application.properties";

    public static final String MESSAGE_PROPERTY = "classpath:message.properties";

    public static final String PATH_TEMPLATES = "/templates/";

    public static final String CHARACTER_ENCODING = "UTF-8";

    public static final String SUFFIX_TEMPLATES = ".html";

    public static final String CSS_LOCATION = "/static/css/";
    public static final String CSS_HANDLER = CSS_LOCATION + "**";

    public static final String JS_LOCATION = "/static/js/";
    public static final String JS_HANDLER = JS_LOCATION + "**";

    public static final String IMG_LOCATION = "/static/img/";
    public static final String IMG_HANDLER = IMG_LOCATION + "**";

    public static final Long MAX_UPLOAD_SIZE = 50000000L;
}
