package online.nwen.server.service.exception;

import online.nwen.server.bo.ResponseCode;

public class ServiceException extends RuntimeException {
    private ResponseCode responseCode;

    public ServiceException(ResponseCode responseCode) {
        super();
        this.responseCode = responseCode;
    }

    public ServiceException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public ServiceException(String message, Throwable cause, ResponseCode responseCode) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    public ServiceException(Throwable cause, ResponseCode responseCode) {
        super(cause);
        this.responseCode = responseCode;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ResponseCode responseCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
