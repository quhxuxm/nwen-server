package online.nwen.server.service.api;

import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.domain.SecurityToken;

public interface ISecurityService {
    AuthenticationResponseBo authenticate(AuthenticationRequestBo authenticationRequestBo);

    void verifyJwtToken(String token);

    String refreshJwtToken(SecurityContextBo old);

    SecurityContextBo parseJwtToken(String token);

    String getSecurityTokenFromCurrentThread();

    void setSecurityTokenToCurrentThread(String securityToken);

    void clearSecurityTokenFromCurrentThread();

    boolean isSecurityTokenDisabled(String securityToken);

    SecurityToken markSecurityTokenDisabled(String securityToken);
}
