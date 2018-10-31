package online.nwen.server.service.api;

import online.nwen.server.service.api.exception.ServiceException;
import online.nwen.server.service.api.payload.AuthenticateRequestPayload;
import online.nwen.server.service.api.payload.AuthenticateResponsePayload;
import online.nwen.server.service.api.payload.RegisterRequestPayload;
import online.nwen.server.service.api.payload.RegisterResponsePayload;

public interface IAuthorService {
    RegisterResponsePayload register(RegisterRequestPayload registerRequestPayload) throws ServiceException;

    AuthenticateResponsePayload authenticate(AuthenticateRequestPayload authenticateRequestPayload)
            throws ServiceException;
}
