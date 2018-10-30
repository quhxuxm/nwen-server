package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.payload.ExceptionPayload;

abstract class AbstractEntryController {
    HttpExecutorResponse<ExceptionPayload> generateExceptionEntryResponse(ExecutorException.Code code) {
        HttpExecutorResponse<ExceptionPayload> response = new HttpExecutorResponse<>();
        ExceptionPayload exceptionPayload = new ExceptionPayload();
        exceptionPayload.setCode(code);
        response.setPayload(exceptionPayload);
        response.setSuccess(false);
        return response;
    }

    boolean verifyInput(IExecutorRequest<?> request) {
        return request.getPayload() != null;
    }
}
