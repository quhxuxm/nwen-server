package online.nwen.server.controller;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.payload.ExceptionPayload;
import org.springframework.web.bind.annotation.RequestBody;

abstract class AbstractEntryController {
    private IExecutorInvoker executorInvoker;

    AbstractEntryController(IExecutorInvoker executorInvoker) {
        this.executorInvoker = executorInvoker;
    }

    HttpExecutorResponse<ExceptionPayload> generateExceptionEntryResponse(ExecutorException.Code code) {
        HttpExecutorResponse<ExceptionPayload> response = new HttpExecutorResponse<>();
        ExceptionPayload exceptionPayload = new ExceptionPayload();
        exceptionPayload.setCode(code);
        response.setPayload(exceptionPayload);
        response.setSuccess(false);
        return response;
    }

    private <RequestPayloadType> boolean verifyCommonInput(IExecutorRequest<RequestPayloadType> request) {
        return request.getPayload() != null;
    }

    <ResponsePayloadType, RequestPayloadType> HttpExecutorResponse<ResponsePayloadType> service(
            @RequestBody HttpExecutorRequest<RequestPayloadType> request,
            IExecutor<ResponsePayloadType, RequestPayloadType> executor, boolean isSecurity)
            throws ExecutorException {
        if (!this.verifyCommonInput(request)) {
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        HttpExecutorResponse<ResponsePayloadType> response = new HttpExecutorResponse<>();
        this.executorInvoker.invoke(executor, request, response, isSecurity);
        return response;
    }
}
