package online.nwen.server.service.api.exception;

public class ServiceException extends Exception {
    public enum Code {
        SECURE_TOKEN_EXPIRED,
        SERVICE_ERROR,
    }

    private Code code;

    public ServiceException(Code code) {
        this.code = code;
    }

    public ServiceException(String message, Code code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Code code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, Code code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                            Code code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }
}
