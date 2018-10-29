package online.nwen.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.configuration.ServiceConfiguration;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.SecurityContext;
import online.nwen.server.service.api.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class SecurityService implements ISecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private ObjectMapper objectMapper;
    private ServiceConfiguration serviceConfiguration;

    public SecurityService(ServiceConfiguration serviceConfiguration) {
        this.algorithm = Algorithm.HMAC256(serviceConfiguration.getSecuritySecret());
        this.verifier = JWT.require(this.algorithm).withIssuer(serviceConfiguration.getSecurityIssuer()).build();
        this.objectMapper = new ObjectMapper();
        this.serviceConfiguration = serviceConfiguration;
    }

    @Override
    public String generateSecureToken(SecurityContext securityContext) throws ServiceException {
        try {
            return JWT.create().withSubject(this.objectMapper.writeValueAsString(securityContext)).sign(this.algorithm);
        } catch (JsonProcessingException e) {
            logger.error("Fail to generate secure token because of exception.", e);
            throw new ServiceException("Fail to generate secure token because of exception.", e,
                    ServiceException.Code.SERVICE_ERROR);
        }
    }

    @Override
    public void verifySecureToken(String secureToken) throws ServiceException {
        try {
            this.verifier.verify(secureToken);
        } catch (TokenExpiredException e) {
            logger.error("Fail to verify secure token because of token expired.", e);
            throw new ServiceException("Fail to verify secure token because of token expired.", e,
                    ServiceException.Code.SERVICE_ERROR);
        } catch (Exception e) {
            logger.error("Fail to verify secure token because of exception.", e);
            throw new ServiceException("Fail to verify secure token because of exception.", e,
                    ServiceException.Code.SERVICE_ERROR);
        }
    }

    @Override
    public SecurityContext parseSecurityContext(String secureToken) throws ServiceException {
        DecodedJWT decodedJWT = JWT.decode(secureToken);
        try {
            return this.objectMapper.readValue(decodedJWT.getSubject(), SecurityContext.class);
        } catch (IOException e) {
            logger.error("Fail to decode secure token because of exception.", e);
            throw new ServiceException("Fail to decode secure token because of exception.", e,
                    ServiceException.Code.SERVICE_ERROR);
        }
    }

    @Override
    public SecurityContext refreshSecurityContext(SecurityContext securityContext) {
        return new SecurityContext(
                System.currentTimeMillis() + this.serviceConfiguration.getSecurityContextRefreshInterval());
    }
}
