package online.nwen.server.executor.api.exception;

public class ExecutorException extends Exception {
    public enum Code {
        INPUT_ERROR,
        AUTH_ERROR,
        REGISTER_USERNAME_EMPTY,
        REGISTER_PASSWORD_EMPTY,
        REGISTER_NICKNAME_EMPTY,
        REGISTER_USERNAME_EXIST,
        REGISTER_NICKNAME_EXIST,
        CREATE_ARTICLE_ANTHOLOGY_ID_IS_EMPTY,
        CREATE_ARTICLE_ANTHOLOGY_NOT_EXIST,
        CREATE_ARTICLE_ANTHOLOGY_NOT_BELONG_TO_AUTHOR,
        CREATE_ARTICLE_TITLE_IS_EMPTY,
        CREATE_ARTICLE_TITLE_IS_TOO_LONG,
        CREATE_ANTHOLOGY_TITLE_IS_EMPTY,
        CREATE_ANTHOLOGY_TITLE_IS_TOO_LONG,
        SYS_ERROR;
    }

    private Code code;

    public ExecutorException(Code code) {
        this.code = code;
    }

    public ExecutorException(String message, Code code) {
        super(message);
        this.code = code;
    }

    public ExecutorException(String message, Throwable cause, Code code) {
        super(message, cause);
        this.code = code;
    }

    public ExecutorException(Throwable cause, Code code) {
        super(cause);
        this.code = code;
    }

    public ExecutorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                             Code code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }
}
