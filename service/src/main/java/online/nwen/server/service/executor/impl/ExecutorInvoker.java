package online.nwen.server.service.executor.impl;

import online.nwen.server.service.executor.api.*;

public class ExecutorInvoker implements IExecutorInvoker {
    @Override
    public <ResponsePayload, RequestPayload> void invoke(IExecutor<ResponsePayload, RequestPayload> executor,
                                                         IExecutorRequest<RequestPayload> request,
                                                         IExecutorResponse<ResponsePayload> response)
            throws ExecutorException {
    }
}
