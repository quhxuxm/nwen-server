package online.nwen.server.executor.api.payload;

import online.nwen.server.executor.api.exception.ExecutorException;

public class ExceptionPayload {
    private ExecutorException.Code code;

    public ExecutorException.Code getCode() {
        return code;
    }

    public void setCode(ExecutorException.Code code) {
        this.code = code;
    }
}
