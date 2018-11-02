package online.nwen.server.executor.api.exception;

public class ExecutorException extends Exception {
    public enum Code {
        INPUT_ERROR,
        AUTH_ERROR,
        USERNAME_IS_EMPTY,
        PASSWORD_IS_EMPTY,
        NICKNAME_IS_EMPTY,
        USERNAME_EXIST,
        NICKNAME_EXIST,
        ANTHOLOGY_ID_IS_EMPTY,
        ANTHOLOGY_NOT_EXIST,
        NOT_ANTHOLOGY_PARTICIPANT,
        NOT_ARTICLE_OWNER,
        NOT_ANTHOLOGY_OWNER,
        ARTICLE_ID_IS_EMPTY,
        ARTICLE_TITLE_IS_EMPTY,
        ARTICLE_TITLE_IS_TOO_LONG,
        ARTICLE_NOT_EXIST,
        ANTHOLOGY_TITLE_IS_EMPTY,
        ANTHOLOGY_TITLE_IS_TOO_LONG,
        CURRENT_AUTHOR_NOT_EXIST,
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
