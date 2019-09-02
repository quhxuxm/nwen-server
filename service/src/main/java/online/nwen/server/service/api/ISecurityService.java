package online.nwen.server.service.api;

import com.auth0.jwt.interfaces.DecodedJWT;
import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.domain.BlackListSecurityToken;

public interface ISecurityService {
    AuthenticationResponseBo authenticate(AuthenticationRequestBo authenticationRequestBo);

    void verifyJwtToken(String token);

    String parseUsernameFromJwtToken(String token);

    DecodedJWT parseJwtToken(String token);

    String getSecurityToken();

    void setSecurityToken(String securityToken);

    void clearSecurityToken();

    boolean isSecurityTokenInBlackList(String securityToken);

    BlackListSecurityToken markSecurityTokenInBlackList(String securityToken);
}
