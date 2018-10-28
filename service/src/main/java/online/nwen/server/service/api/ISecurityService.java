package online.nwen.server.service.api;

public interface ISecurityService {
    String generateSecureToken(ISecurityContext securityContext);

    ISecurityContext parseSecurityContext(String secureToken);
}
