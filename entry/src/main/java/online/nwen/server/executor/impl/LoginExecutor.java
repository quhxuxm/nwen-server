package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.payload.LoginRequestPayload;
import org.springframework.stereotype.Service;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.payload.LoginResponsePayload;

@Service
class LoginExecutor implements IExecutor<LoginResponsePayload, LoginRequestPayload> {
    @Override
    public void exec(IExecutorRequest<LoginRequestPayload> request, IExecutorResponse<LoginResponsePayload> response)
            throws ExecutorException {
    }
}
