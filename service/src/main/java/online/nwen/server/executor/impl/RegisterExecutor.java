package online.nwen.server.executor.impl;

import online.nwen.server.domain.Anthology;
import online.nwen.server.domain.Author;
import online.nwen.server.domain.Role;
import online.nwen.server.executor.api.IExecutor;
import online.nwen.server.executor.api.IExecutorRequest;
import online.nwen.server.executor.api.IExecutorResponse;
import online.nwen.server.executor.api.exception.ExecutorException;
import online.nwen.server.executor.api.payload.RegisterRequestPayload;
import online.nwen.server.executor.api.payload.RegisterResponsePayload;
import online.nwen.server.service.api.IAnthologyService;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.ISecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RegisterExecutor implements IExecutor<RegisterResponsePayload, RegisterRequestPayload> {
    private static final Logger logger = LoggerFactory.getLogger(RegisterExecutor.class);
    private IAuthorService authorService;
    private IAnthologyService anthologyService;

    public RegisterExecutor(IAuthorService authorService,
                            IAnthologyService anthologyService) {
        this.authorService = authorService;
        this.anthologyService = anthologyService;
    }

    @Override
    public void exec(IExecutorRequest<RegisterRequestPayload> request,
                     IExecutorResponse<RegisterResponsePayload> response, ISecurityContext securityContext)
            throws ExecutorException {
        RegisterRequestPayload registerRequestPayload = request.getPayload();
        if (StringUtils.isEmpty(registerRequestPayload.getUsername())) {
            logger.error("Fail to register author because of the username is empty.");
            throw new ExecutorException(ExecutorException.Code.USERNAME_IS_EMPTY);
        }
        if (StringUtils.isEmpty(registerRequestPayload.getPassword())) {
            logger.error("Fail to register author because of the password is empty.");
            throw new ExecutorException(ExecutorException.Code.PASSWORD_IS_EMPTY);
        }
        if (StringUtils.isEmpty(registerRequestPayload.getNickname())) {
            logger.error("Fail to register author because of the nickname is empty.");
            throw new ExecutorException(ExecutorException.Code.NICKNAME_IS_EMPTY);
        }
        logger.debug("Begin to register author with {}", registerRequestPayload);
        if (this.authorService.existsByUsername(registerRequestPayload.getUsername())) {
            logger.error("Fail to register author because of username exist.");
            throw new ExecutorException(ExecutorException.Code.USERNAME_EXIST);
        }
        if (this.authorService.existsByNickname(registerRequestPayload.getNickname())) {
            logger.error("Fail to register author because of nickname exist.");
            throw new ExecutorException(ExecutorException.Code.NICKNAME_EXIST);
        }
        Author author = new Author();
        author.setNickname(registerRequestPayload.getNickname());
        author.setUsername(registerRequestPayload.getUsername());
        author.setPassword(registerRequestPayload.getPassword());
        author.setRegisterDate(new Date());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.AUTHOR);
        author.setRoles(roles);
        this.authorService.save(author);
        Anthology anthology = new Anthology();
        anthology.setAuthorId(author.getId());
        anthology.setCreateDate(new Date());
        this.anthologyService.save(anthology);
        author.setDefaultAnthologyId(anthology.getId());
        this.authorService.save(author);
        RegisterResponsePayload registerResponsePayload = new RegisterResponsePayload();
        registerResponsePayload.setAuthorId(author.getId());
        response.setPayload(registerResponsePayload);
    }
}
