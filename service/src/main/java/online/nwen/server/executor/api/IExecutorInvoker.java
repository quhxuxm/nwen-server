package online.nwen.server.executor.api;

import online.nwen.server.executor.api.exception.ExecutorException;

public interface IExecutorInvoker {
    <ResponsePayload, RequestPayload> void invoke(
            IExecutor<ResponsePayload, RequestPayload> executor, IExecutorRequest<RequestPayload> request,
            IExecutorResponse<ResponsePayload> response, boolean isSecure) throws ExecutorException;
}
