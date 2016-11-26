package ru.donstu.cloudstorage.validator;

import java.util.regex.Pattern;

/**
 * Инструмент для проверки регулярных выражений
 *
 * @author v.solomasov
 */
public class RegexUtil {

    public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,50}$";

    public static final String PATTERN_LOGIN = "^[a-z0-9A-Z].{3,50}";

    public static final String PATTERN_EMAIL = "^(?=.*[@])(?=.*[.]).{3,50}$";

    public static final boolean checkRegEx(String word, String pattern) {
        return Pattern.compile(pattern).matcher(word).matches();
    }
}
