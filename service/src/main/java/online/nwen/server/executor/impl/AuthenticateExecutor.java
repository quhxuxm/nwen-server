package online.nwen.server.executor.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.AuthenticateRequestPayload;
import online.nwen.server.executor.api.payload.AuthenticateResponsePayload;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class AuthenticateExecutor implements IExecutor<AuthenticateResponsePayload, AuthenticateRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateExecutor.class);
    private ISecurityService securityService;
    private IAuthorRepository authorRepository;

    public AuthenticateExecutor(ISecurityService securityService,
                                IAuthorRepository authorRepository) {
        this.securityService = securityService;
        this.authorRepository = authorRepository;
    }

    @Override
    public void exec(IExecutorRequest<AuthenticateRequestPayload> request,
                     IExecutorResponse<AuthenticateResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        logger.debug("Begin to do authentication.");
        AuthenticateRequestPayload requestPayload = request.getPayload();
        if (StringUtils.isEmpty(requestPayload.getUsername())) {
            logger.error("Fail to do authentication because of the username is empty.");
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        if (StringUtils.isEmpty(requestPayload.getPassword())) {
            logger.error("Fail to do authentication because of the password is empty.");
            throw new ExecutorException(ExecutorException.Code.INPUT_ERROR);
        }
        Author author = this.authorRepository.findByUsername(requestPayload.getUsername());
        if (author == null) {
            throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
        }
        if (!requestPayload.getPassword().equals(author.getPassword())) {
            throw new ExecutorException(ExecutorException.Code.AUTH_ERROR);
        }
        author.setLastLoginDate(new Date());
        try {
            this.authorRepository.save(author);
        } catch (Exception e) {
            throw new ExecutorException(ExecutorException.Code.SYS_ERROR);
        }
        AuthenticateResponsePayload responsePayload = new AuthenticateResponsePayload();
        responsePayload.setUsername(author.getUsername());
        logger.debug("Begin to generate security context for new authentication.");
        ISecurityContext newSecurityContext =
                this.securityService.createSecurityContext(responsePayload);
        try {
            String secureToken = this.securityService.generateSecureToken(newSecurityContext);
            logger.debug("New security token [{}] generated for author {}.", secureToken,
                    responsePayload.getUsername());
            response.setPayload(responsePayload);
            response.getHeader().put(IExecutorResponse.ResponseHeader.SECURE_TOKEN, secureToken);
        } catch (ServiceException e) {
            logger.error("Fail to generate security token for author [{}] because of exception.",
                    responsePayload.getUsername(), e);
            throw new ExecutorException(e, ExecutorException.Code.AUTH_ERROR);
        }
    }
}
