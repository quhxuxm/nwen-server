package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.payload.RegisterRequestPayload;
import online.nwen.server.payload.RegisterResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import org.springframework.stereotype.Service;

@Service
public class RegisterExecutor implements IExecutor<RegisterResponsePayload, RegisterRequestPayload> {
    @Override
    public void exec(IExecutorRequest<RegisterRequestPayload> request,
                     IExecutorResponse<RegisterResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
    }
}
