package online.nwen.server.controller;

import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.ExceptionPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionController extends AbstractEntryController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    ExceptionController() {
        super(null);
    }

    @ExceptionHandler(value = ExecutorException.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    HttpExecutorResponse<ExceptionPayload> onExecutorException(ExecutorException e) {
        logger.debug("Exception happen, convert exception to exception response. Exceptions is:\n", e);
        return this.generateExceptionEntryResponse(e.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    HttpExecutorResponse<ExceptionPayload> onGeneralException(Exception e) {
        ExecutorException executorException = new ExecutorException(ExecutorException.Code.SYS_ERROR);
        logger.debug("General exception happen, convert exception to exception response. Exceptions is:\n", e);
        return this.generateExceptionEntryResponse(executorException.getCode());
    }
}