package online.nwen.server.service.executor.impl;

import online.nwen.server.service.executor.api.ExecutorException;
import online.nwen.server.service.executor.api.IExecutor;
import online.nwen.server.service.executor.api.IExecutorRequest;
import online.nwen.server.service.executor.api.IExecutorResponse;
import online.nwen.server.service.payload.LoginRequestPayload;
import online.nwen.server.service.payload.LoginResponsePayload;
import org.springframework.stereotype.Service;

@Service
class LoginExecutor implements IExecutor<LoginResponsePayload, LoginRequestPayload> {
    @Override
    public IExecutorResponse<LoginResponsePayload> exec(IExecutorRequest<LoginRequestPayload> request)
            throws ExecutorException {
        return null;
    }
}
