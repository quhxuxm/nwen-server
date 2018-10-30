package online.nwen.server.service.api;

import online.nwen.server.service.api.exception.ServiceException;

public interface ISecurityService {
    String generateSecureToken(ISecurityContext securityContext) throws ServiceException;

    void verifySecureToken(String secureToken) throws ServiceException;

    ISecurityContext parseSecurityContext(String secureToken) throws ServiceException;

    ISecurityContext refreshSecurityContext(ISecurityContext securityContext);

    ISecurityContext createSecurityContext(String username);
}
