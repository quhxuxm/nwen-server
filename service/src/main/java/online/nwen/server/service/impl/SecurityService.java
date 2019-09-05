package online.nwen.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.bo.AuthenticationRequestBo;
import online.nwen.server.bo.AuthenticationResponseBo;
import online.nwen.server.bo.ResponseCode;
import online.nwen.server.bo.SecurityContextBo;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.dao.api.ISecurityTokenDao;
import online.nwen.server.dao.api.IUserDao;
import online.nwen.server.domain.SecurityToken;
import online.nwen.server.domain.User;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static online.nwen.server.bo.ResponseCode.*;

@Service
class SecurityService implements ISecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private static final ThreadLocal<SecurityContextBo> SECURITY_CONTEXT_HOLDER = new ThreadLocal<>();
    private ServerConfiguration serverConfiguration;
    private IUserDao userDao;
    private Algorithm jwtAlgorithm;
    private JWTVerifier verifier;
    private ISecurityTokenDao securityTokenDao;
    private ObjectMapper objectMapper;

    SecurityService(ServerConfiguration serverConfiguration, IUserDao userDao, ISecurityTokenDao securityTokenDao,
                    ObjectMapper objectMapper) {
        this.serverConfiguration = serverConfiguration;
        this.userDao = userDao;
        this.securityTokenDao = securityTokenDao;
        this.objectMapper = objectMapper;
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
        SecurityContextBo securityContextBo = new SecurityContextBo();
        long current = System.currentTimeMillis();
        Date expireAt = new Date(current + this.serverConfiguration.getJwtExpireInterval());
        securityContextBo.setUsername(user.getUsername());
        Date refreshableTill = new Date(current + this.serverConfiguration.getJwtRefreshableInterval());
        securityContextBo.setRefreshAbleTill(refreshableTill);
        String result = null;
        try {
            String securityContextString = this.objectMapper.writeValueAsString(securityContextBo);
            result = JWT.create().withSubject(securityContextString).withExpiresAt(expireAt).withIssuer(this.serverConfiguration.getJwtIssuer()).sign(this.jwtAlgorithm);
        } catch (JsonProcessingException e) {
            logger.error("Fail to generate security token because of exception.", e);
            throw new ServiceException(SECURITY_TOKEN_GENERATE_FAIL);
        }
        SecurityToken securityToken = new SecurityToken();
        securityToken.setToken(result);
        securityToken.setExpireAt(expireAt);
        securityToken.setRefreshableTill(refreshableTill);
        securityToken.setCreateTime(new Date());
        securityToken.setDisabled(false);
        this.securityTokenDao.save(securityToken);
        return result;
    }

    @Override
    public void verifyJwtToken(String token) {
        try {
            this.verifier.verify(token);
        } catch (TokenExpiredException e) {
            logger.warn("Security token expired.");
            throw new ServiceException(SECURITY_TOKEN_EXPIRED);
        } catch (Exception e) {
            logger.error("Fail to verify security token because of exception.", e);
            throw new ServiceException(SECURITY_CHECK_FAIL);
        }
    }

    @Override
    public SecurityContextBo parseJwtToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        try {
            String securityContextBoString = decodedJWT.getSubject();
            return this.objectMapper.readValue(securityContextBoString, SecurityContextBo.class);
        } catch (Exception e) {
            logger.error("Fail to parse security token because of exception.", e);
            throw new ServiceException(SECURITY_TOKEN_PARSE_FAIL);
        }
    }

    @Override
    public String refreshJwtToken(SecurityContextBo old) {
        User user = this.userDao.getByUsername(old.getUsername());
        if (user == null) {
            throw new ServiceException(USER_NOT_EXIST);
        }
        return this.generateJwtToken(user);
    }

    @Override
    public SecurityContextBo checkAndGetSecurityContextFromCurrentThread() {
        SecurityContextBo result = SECURITY_CONTEXT_HOLDER.get();
        if (result == null) {
            throw new ServiceException(ResponseCode.SECURITY_CHECK_FAIL);
        }
        return result;
    }

    @Override
    public void setSecurityContextToCurrentThread(SecurityContextBo context) {
        SECURITY_CONTEXT_HOLDER.set(context);
    }

    @Override
    public void clearSecurityContextFromCurrentThread() {
        SECURITY_CONTEXT_HOLDER.remove();
    }

    @Override
    public boolean isSecurityTokenDisabled(String securityToken) {
        SecurityToken securityTokenObj = this.securityTokenDao.getByToken(securityToken);
        if (securityTokenObj == null) {
            return false;
        }
        return securityTokenObj.isDisabled();
    }

    @Override
    public SecurityToken markSecurityTokenDisabled(String securityToken) {
        SecurityToken securityTokenObj = this.securityTokenDao.getByToken(securityToken);
        if (securityTokenObj == null) {
            return null;
        }
        securityTokenObj.setDisabled(true);
        return this.securityTokenDao.save(securityTokenObj);
    }
}
