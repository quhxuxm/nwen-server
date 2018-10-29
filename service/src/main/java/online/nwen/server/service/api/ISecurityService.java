package online.nwen.server.service.api;

import online.nwen.server.service.api.exception.ServiceException;

public interface ISecurityService {
    String generateSecureToken(SecurityContext securityContext) throws ServiceException;

    void verifySecureToken(String secureToken) throws ServiceException;

    SecurityContext parseSecurityContext(String secureToken) throws ServiceException;

    SecurityContext refreshSecurityContext(SecurityContext securityContext);
}
