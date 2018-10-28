package online.nwen.server.service.executor.api;

public interface IExecutorInvoker {
    <ResponsePayload, RequestPayload> void invoke(
            IExecutor<ResponsePayload, RequestPayload> executor, IExecutorRequest<RequestPayload> request,
            IExecutorResponse<ResponsePayload> response) throws ExecutorException;
}
