package ru.donstu.cloudstorage.exception;

/**
 * Исключение AES
 *
 * @author v.solomasov
 */
public class AesException extends RuntimeException {

    private static final String TEXT_EXCEPTION = "Произошла непредвиденная ошибка криптографического алгоритма AES";

    public AesException() {
        super(TEXT_EXCEPTION);
    }
}
