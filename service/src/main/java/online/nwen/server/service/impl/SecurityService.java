package online.nwen.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static online.nwen.server.bo.ResponseCode.*;

@Service
class SecurityService implements ISecurityService {
    private static final ThreadLocal<String> SECURITY_TOKEN_HOLDER = new ThreadLocal<>();
    private ServerConfiguration serverConfiguration;
    private IUserDao userDao;
    private Algorithm jwtAlgorithm;
    private JWTVerifier verifier;

    SecurityService(ServerConfiguration serverConfiguration, IUserDao userDao) {
        this.serverConfiguration = serverConfiguration;
        this.userDao = userDao;
        this.jwtAlgorithm = Algorithm.HMAC256(this.serverConfiguration.getJwtSecret());
        this.verifier = JWT.require(this.jwtAlgorithm).withIssuer(serverConfiguration.getJwtIssuer()).build();
    }

    @Override
    public AuthenticationResponseBo authenticate(AuthenticationRequestBo authenticationRequestBo) {
        if (StringUtils.isEmpty(authenticationRequestBo.getUsername())) {
            throw new ServiceException(AUTHENTICATE_USERNAME_IS_EMPTY);
        }
        if (StringUtils.isEmpty(authenticationRequestBo.getPassword())) {
            throw new ServiceException(AUTHENTICATE_PASSWORD_IS_EMPTY);
        }
        User user = this.userDao.getByUsername(authenticationRequestBo.getUsername());
        if (user == null) {
            throw new ServiceException(AUTHENTICATE_USERNAME_NOT_EXIST);
        }
        if (!user.getPassword().equals(authenticationRequestBo.getPassword())) {
            throw new ServiceException(AUTHENTICATE_PASSWORD_NOT_MATCH);
        }
        AuthenticationResponseBo authenticationResponseBo = new AuthenticationResponseBo();
        authenticationResponseBo.setUsername(user.getUsername());
        authenticationResponseBo.setDescription(user.getDescription());
        authenticationResponseBo.setNickname(user.getNickname());
        authenticationResponseBo.setLastLoginTime(user.getLastLoginTime());
        authenticationResponseBo.setRegisteredTime(user.getRegisteredTime());
        authenticationResponseBo.setSecurityToken(this.generateJwtToken(user));
        return authenticationResponseBo;
    }

    private String generateJwtToken(User user) {
        Date expireAt = new Date(System.currentTimeMillis() + this.serverConfiguration.getJwtExpireInterval());
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(expireAt).sign(this.jwtAlgorithm);
    }

    @Override
    public void verifyJwtToken(String token) {
        try {
            this.verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new ServiceException(SECURITY_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new ServiceException(SECURITY_CHECK_FAIL);
        }
    }

    @Override
    public String parseUsernameFromJwtToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        try {
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new ServiceException(SECURITY_TOKEN_PARSE_FAIL);
        }
    }

    @Override
    public String getSecurityToken() {
        return SECURITY_TOKEN_HOLDER.get();
    }

    @Override
    public void setSecurityToken(String securityToken) {
        SECURITY_TOKEN_HOLDER.set(securityToken);
    }

    @Override
    public void clearSecurityToken() {
        SECURITY_TOKEN_HOLDER.remove();
    }
}
