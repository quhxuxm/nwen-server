package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorInvoker;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.SecurityContext;
import online.nwen.server.service.api.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class ExecutorInvoker implements IExecutorInvoker {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorInvoker.class);
    private ISecurityService securityService;

    ExecutorInvoker(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public <ResponsePayload, RequestPayload> void invoke(IExecutor<ResponsePayload, RequestPayload> executor,
                                                         IExecutorRequest<RequestPayload> request,
                                                         IExecutorResponse<ResponsePayload> response, boolean isSecure)
            throws ExecutorException {
        String secureToken = request.getHeader().get(IExecutorRequest.RequestHeader.SECURE_TOKEN);
        if (isSecure && secureToken == null) {
            logger.error("Fail to execute executor because of no secure token given.");
            throw new ExecutorException(ExecutorException.Code.AUTH_INPUT_ERROR);
        }
        if (!isSecure) {
            logger.debug("Begin to invoke a insecure executor [{}].", executor.getClass().getName());
            executor.exec(request, response);
            logger.debug("Success to invoke a insecure executor [{}].", executor.getClass().getName());
            return;
        }
        try {
            this.securityService.verifySecureToken(secureToken);
        } catch (ServiceException e) {
            if (ServiceException.Code.SECURE_TOKEN_EXPIRED == e.getCode()) {
                try {
                    SecurityContext securityContext = this.securityService.parseSecurityContext(secureToken);
                    if (System.currentTimeMillis() > securityContext.getRefreshExpiration()) {
                        logger.error("Can not refresh the secure token because of the refresh expiration exceed.");
                        throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
                    }
                    SecurityContext refreshedSecurityContext =
                            this.securityService.refreshSecurityContext(securityContext);
                    String refreshedSecureToken = this.securityService.generateSecureToken(refreshedSecurityContext);
                    response.getHeader().put(IExecutorResponse.ResponseHeader.SECURE_TOKEN, refreshedSecureToken);
                } catch (ServiceException e1) {
                    logger.error("Can not refresh the secure token because of exception.", e1);
                    throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
                }
            }
        }
        logger.debug("Begin to invoke a secure executor [{}].", executor.getClass().getName());
        executor.exec(request, response);
        logger.debug("Success to invoke a secure executor [{}].", executor.getClass().getName());
    }
}
