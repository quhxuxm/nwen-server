package online.nwen.server.service.api;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;

public interface IExecutorService {
    <ResponsePayload, RequestPayload> void invoke(
            IExecutor<ResponsePayload, RequestPayload> executor, IExecutorRequest<RequestPayload> request,
            IExecutorResponse<ResponsePayload> response, boolean isSecure) throws ExecutorException;
}
