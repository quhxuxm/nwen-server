package online.nwen.server.service.api;

import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;

public interface ISecurityService {
    AuthenticationResponseBo authenticate(AuthenticationRequestBo authenticationRequestBo);

    void verifyJwtToken(String token);

    String parseUsernameFromJwtToken(String token);

    String getSecurityToken();

    void setSecurityToken(String securityToken);

    void clearSecurityToken();
}
