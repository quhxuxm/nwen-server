package online.nwen.server.service.impl;

import online.nwen.server.domain.Author;
import online.nwen.server.domain.Role;
import online.nwen.server.repository.IAuthorRepository;
import online.nwen.server.service.api.IAuthorService;
import online.nwen.server.service.api.exception.ServiceException;
import online.nwen.server.service.api.payload.AuthenticateRequestPayload;
import online.nwen.server.service.api.payload.AuthenticateResponsePayload;
import online.nwen.server.service.api.payload.RegisterRequestPayload;
import online.nwen.server.service.api.payload.RegisterResponsePayload;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
class AuthorService implements IAuthorService {
    private IAuthorRepository authorRepository;

    AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public RegisterResponsePayload register(RegisterRequestPayload registerRequestPayload) throws ServiceException {
        if (this.authorRepository.existsByUsername(registerRequestPayload.getUsername())) {
            throw new ServiceException(ServiceException.Code.AUTHOR_USERNAME_EXIST);
        }
        if (this.authorRepository.existsByNickname(registerRequestPayload.getNickname())) {
            throw new ServiceException(ServiceException.Code.AUTHOR_NICKNAME_EXIST);
        }
        Author author = new Author();
        author.setNickname(registerRequestPayload.getNickname());
        author.setUsername(registerRequestPayload.getUsername());
        author.setPassword(registerRequestPayload.getPassword());
        author.setRegisterDate(new Date());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.AUTHOR);
        author.setRoles(roles);
        try {
            this.authorRepository.save(author);
        } catch (Exception e) {
            throw new ServiceException(ServiceException.Code.SERVICE_ERROR);
        }
        RegisterResponsePayload registerResponsePayload = new RegisterResponsePayload();
        registerResponsePayload.setAuthorId(author.getId());
        return registerResponsePayload;
    }

    @Override
    public AuthenticateResponsePayload authenticate(AuthenticateRequestPayload authenticateRequestPayload)
            throws ServiceException {
        Author author = this.authorRepository.findByUsername(authenticateRequestPayload.getUsername());
        if (author == null) {
            throw new ServiceException(ServiceException.Code.AUTHOR_NOT_EXIST);
        }
        if (!authenticateRequestPayload.getPassword().equals(author.getPassword())) {
            throw new ServiceException(ServiceException.Code.AUTHOR_NOT_EXIST);
        }
        AuthenticateResponsePayload authenticateResponsePayload = new AuthenticateResponsePayload();
        authenticateResponsePayload.setUsername(author.getUsername());
        return authenticateResponsePayload;
    }
}
