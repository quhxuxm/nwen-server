package online.nwen.server.executor.impl;

import online.nwen.server.configuration.ServiceConfiguration;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.payload.AuthenticateRequestPayload;
import online.nwen.server.payload.AuthenticateResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateExecutor implements IExecutor<AuthenticateResponsePayload, AuthenticateRequestPayload> {
    private ISecurityService securityService;
    private ServiceConfiguration serviceConfiguration;

    public AuthenticateExecutor(ISecurityService securityService,
                                ServiceConfiguration serviceConfiguration) {
        this.securityService = securityService;
        this.serviceConfiguration = serviceConfiguration;
    }

    @Override
    public void exec(IExecutorRequest<AuthenticateRequestPayload> request,
                     IExecutorResponse<AuthenticateResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        ISecurityContext newSecurityContext =
                this.securityService.createSecurityContext(request.getPayload().getUsername());
        try {
            String secureToken = this.securityService.generateSecureToken(newSecurityContext);
            AuthenticateResponsePayload responsePayload = new AuthenticateResponsePayload();
            responsePayload.setSecureToken(secureToken);
            response.setPayload(responsePayload);
        } catch (ServiceException e) {
            throw new ExecutorException(e, ExecutorException.Code.AUTH_ERROR);
        }
    }
}
