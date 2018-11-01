package online.nwen.server.service.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.service.api.IExecutorService;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class ExecutorService implements IExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorService.class);
    private ISecurityService securityService;

    ExecutorService(ISecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public <ResponsePayload, RequestPayload> void invoke(IExecutor<ResponsePayload, RequestPayload> executor,
                                                         IExecutorRequest<RequestPayload> request,
                                                         IExecutorResponse<ResponsePayload> response, boolean isSecure)
            throws ExecutorException {
        String secureToken = request.getHeader().get(IExecutorRequest.RequestHeader.SECURE_TOKEN);
        ISecurityContext securityContext = null;
        if (secureToken != null) {
            try {
                securityContext = this.securityService.parseSecurityContext(secureToken);
            } catch (ServiceException e) {
                logger.debug(
                        "Fail to parse security context from secure token  because of exception.",
                        e);
            }
        }
        if (isSecure && secureToken == null) {
            logger.error("Fail to execute executor because of no secure token given.");
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        if (!isSecure) {
            logger.debug("Begin to invoke a insecure executor [{}].", executor.getClass().getName());
            executor.exec(request, response, securityContext);
            logger.debug("Success to invoke a insecure executor [{}].", executor.getClass().getName());
            return;
        }
        try {
            this.securityService.verifySecureToken(secureToken);
        } catch (ServiceException e) {
            if (ServiceException.Code.SECURE_TOKEN_EXPIRED != e.getCode()) {
                throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
            }
            if (securityContext == null) {
                logger.error(
                        "Can not refresh the secure token because of can not parse secure context from secure token.");
                throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
            }
            try {
                if (System.currentTimeMillis() > securityContext.getRefreshExpiration()) {
                    logger.error("Can not refresh the secure token because of the refresh expiration exceed.");
                    throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
                }
                securityContext =
                        this.securityService.refreshSecurityContext(securityContext);
                String refreshedSecureToken = this.securityService.generateSecureToken(securityContext);
                response.getHeader().put(IExecutorResponse.ResponseHeader.SECURE_TOKEN, refreshedSecureToken);
            } catch (ServiceException e1) {
                logger.error("Can not refresh the secure token because of exception.", e1);
                throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
            }
        }
        logger.debug("Begin to invoke a secure executor [{}].", executor.getClass().getName());
        executor.exec(request, response, securityContext);
        logger.debug("Success to invoke a secure executor [{}].", executor.getClass().getName());
    }
}
