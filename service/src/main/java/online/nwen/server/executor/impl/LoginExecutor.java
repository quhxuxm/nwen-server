package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.ExecutorException;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.payload.LoginRequestPayload;
import online.nwen.server.payload.LoginResponsePayload;
import org.springframework.stereotype.Service;

@Service
class LoginExecutor implements IExecutor<LoginResponsePayload, LoginRequestPayload> {
    @Override
    public IExecutorResponse<LoginResponsePayload> exec(IExecutorRequest<LoginRequestPayload> request)
            throws ExecutorException {
        return null;
    }
}
