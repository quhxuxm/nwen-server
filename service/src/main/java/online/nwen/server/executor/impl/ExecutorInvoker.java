package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.*;

public class ExecutorInvoker implements IExecutorInvoker {
    @Override
    public <ResponsePayload, RequestPayload> void invoke(IExecutor<ResponsePayload, RequestPayload> executor,
                                                         IExecutorRequest<RequestPayload> request,
                                                         IExecutorResponse<ResponsePayload> response, boolean isSecure)
            throws ExecutorException {
        String secureToken = request.getHeader().get(IExecutorRequest.RequestHeader.SECURE_TOKEN);
        if (isSecure && secureToken == null) {
        }
    }
}
