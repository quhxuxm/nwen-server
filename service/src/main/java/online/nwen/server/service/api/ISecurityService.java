package online.nwen.server.service.api;

import online.nwen.server.service.api.exception.SecurityServiceException;
import online.nwen.server.executor.api.payload.AuthenticateResponsePayload;

public interface ISecurityService {
    String generateSecureToken(ISecurityContext securityContext) throws SecurityServiceException;

    void verifySecureToken(String secureToken) throws SecurityServiceException;

    ISecurityContext parseSecurityContext(String secureToken) throws SecurityServiceException;

    ISecurityContext refreshSecurityContext(ISecurityContext securityContext);

    ISecurityContext createSecurityContext(AuthenticateResponsePayload authenticateResponsePayload);
}
