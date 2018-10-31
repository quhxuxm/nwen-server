package online.nwen.server.executor.impl;

import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.exception.ServiceException;
import online.nwen.server.service.api.payload.RegisterRequestPayload;
import online.nwen.server.service.api.payload.RegisterResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterExecutor implements IExecutor<RegisterResponsePayload, RegisterRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterExecutor.class);
    private IAuthorService authorService;

    public RegisterExecutor(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void exec(IExecutorRequest<RegisterRequestPayload> request,
                     IExecutorResponse<RegisterResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        RegisterRequestPayload registerRequestPayload = request.getPayload();
        if (StringUtils.isEmpty(registerRequestPayload.getUsername())) {
            logger.error("Fail to register author because of the username is empty.");
            throw new ExecutorException(ExecutorException.Code.REGISTER_USERNAME_EMPTY);
        }
        if (StringUtils.isEmpty(registerRequestPayload.getPassword())) {
            logger.error("Fail to register author because of the password is empty.");
            throw new ExecutorException(ExecutorException.Code.REGISTER_PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(registerRequestPayload.getNickname())) {
            logger.error("Fail to register author because of the nickname is empty.");
            throw new ExecutorException(ExecutorException.Code.REGISTER_NICKNAME_EMPTY);
        }
        RegisterResponsePayload registerResponsePayload = null;
        try {
            registerResponsePayload = this.authorService.register(registerRequestPayload);
            response.setPayload(registerResponsePayload);
        } catch (ServiceException e) {
            if (e.getCode() == ServiceException.Code.AUTHOR_NICKNAME_EXIST) {
                logger.error("Fail to register author because of the nickname exist.");
                throw new ExecutorException(e, ExecutorException.Code.REGISTER_NICKNAME_EXIST);
            }
            if (e.getCode() == ServiceException.Code.AUTHOR_USERNAME_EXIST) {
                logger.error("Fail to register author because of the username exist.");
                throw new ExecutorException(e, ExecutorException.Code.REGISTER_USERNAME_EXIST);
            }
            logger.error("Fail to register author [{}] because of exception.",
                    registerRequestPayload.getUsername(), e);
            throw new ExecutorException(e, ExecutorException.Code.SYS_ERROR);
        }
    }
}
