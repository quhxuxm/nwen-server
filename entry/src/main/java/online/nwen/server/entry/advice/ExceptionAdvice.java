package online.nwen.server.entry.advice;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    ExceptionAdvice() {
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseCode> onServiceException(Exception e) {
        logger.error("Exception happen, convert exception to exception response. Exceptions is:\n", e);
        if (ServiceException.class.isAssignableFrom(e.getClass())) {
            ServiceException serviceException = (ServiceException) e;
            switch (serviceException.getResponseCode()) {
                case SECURITY_CHECK_FAIL:
                case SECURITY_TOKEN_EXPIRED:
                    return new ResponseEntity<>(serviceException.getResponseCode(), HttpStatus.UNAUTHORIZED);
                default:
                    return new ResponseEntity<>(serviceException.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        logger.error("Unknown exception happen, convert to system error. Exception is: \n", e);
        return new ResponseEntity<>(ResponseCode.SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
