package ru.donstu.cloudstorage.validator.util;

import java.util.regex.Pattern;

/**
 * Инструмент для проверки регулярных выражений
 *
 * @author v.solomasov
 */
public class RegexUtil {

    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,50}$";

    public static final String PATTERN_LOGIN_1 = "^[a-z0-9]+";

    public static final String PATTERN_LOGIN_2 = "^(?=.*[a-z]).{3,50}$";

    public static final String PATTERN_EMAIL = "^(?=.*[@])(?=.*[.]).{3,50}$";

    public static final boolean checkRegEx(String word, String pattern) {
        return Pattern.compile(pattern).matcher(word).matches();
    }
}
