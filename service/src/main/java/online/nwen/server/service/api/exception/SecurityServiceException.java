package online.nwen.server.service.api.exception;

public class SecurityServiceException extends Exception {
    public enum Code {
        SECURE_TOKEN_EXPIRED,
        SECURE_TOKEN_PARSE_ERROR,
    }

    private Code code;

    public SecurityServiceException(Code code) {
        this.code = code;
    }

    public SecurityServiceException(String message, Code code) {
        super(message);
        this.code = code;
    }

    public SecurityServiceException(String message, Throwable cause, Code code) {
        super(message, cause);
        this.code = code;
    }

    public SecurityServiceException(Throwable cause, Code code) {
        super(cause);
        this.code = code;
    }

    public SecurityServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                                    Code code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }
}
