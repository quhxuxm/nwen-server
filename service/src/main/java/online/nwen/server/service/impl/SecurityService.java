package online.nwen.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.configuration.GlobalConfiguration;
import online.nwen.server.executor.api.payload.AuthenticateResponsePayload;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.ISecurityService;
import online.nwen.server.service.api.exception.SecurityServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
class SecurityService implements ISecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private ObjectMapper objectMapper;
    private GlobalConfiguration globalConfiguration;

    public SecurityService(GlobalConfiguration globalConfiguration) {
        this.algorithm = Algorithm.HMAC256(globalConfiguration.getSecuritySecret());
        this.verifier = JWT.require(this.algorithm).withIssuer(globalConfiguration.getSecurityIssuer()).build();
        this.objectMapper = new ObjectMapper();
        this.globalConfiguration = globalConfiguration;
    }

    @Override
    public String generateSecureToken(ISecurityContext securityContext) throws SecurityServiceException {
        try {
            Date securityTokenExpireAt =
                    new Date(System.currentTimeMillis() + this.globalConfiguration.getSecurityContextExpireInterval());
            return JWT.create().withSubject(this.objectMapper.writeValueAsString(securityContext))
                    .withExpiresAt(securityTokenExpireAt).sign(this.algorithm);
        } catch (JsonProcessingException e) {
            logger.error("Fail to generate secure token because of exception.", e);
            throw new SecurityServiceException("Fail to generate secure token because of exception.", e,
                    SecurityServiceException.Code.SECURE_TOKEN_PARSE_ERROR);
        }
    }

    @Override
    public void verifySecureToken(String secureToken) throws SecurityServiceException {
        try {
            this.verifier.verify(secureToken);
        } catch (TokenExpiredException e) {
            logger.debug("Fail to verify secure token because of token expired.", e);
            throw new SecurityServiceException("Fail to verify secure token because of token expired.", e,
                    SecurityServiceException.Code.SECURE_TOKEN_EXPIRED);
        } catch (Exception e) {
            logger.error("Fail to verify secure token because of exception.", e);
            throw new SecurityServiceException("Fail to verify secure token because of exception.", e,
                    SecurityServiceException.Code.SECURE_TOKEN_PARSE_ERROR);
        }
    }

    @Override
    public ISecurityContext parseSecurityContext(String secureToken) throws SecurityServiceException {
        DecodedJWT decodedJWT = JWT.decode(secureToken);
        try {
            return this.objectMapper.readValue(decodedJWT.getSubject(), SecurityContext.class);
        } catch (IOException e) {
            logger.error("Fail to decode secure token because of exception.", e);
            throw new SecurityServiceException("Fail to decode secure token because of exception.", e,
                    SecurityServiceException.Code.SECURE_TOKEN_PARSE_ERROR);
        }
    }

    @Override
    public ISecurityContext refreshSecurityContext(ISecurityContext securityContext) {
        return createSecurityContentInstance(securityContext.getAuthorId()
        );
    }

    @Override
    public ISecurityContext createSecurityContext(AuthenticateResponsePayload authenticateResponsePayload) {
        return createSecurityContentInstance(
                authenticateResponsePayload.getAuthorId());
    }

    private ISecurityContext createSecurityContentInstance(String authorId) {
        SecurityContext result = new SecurityContext();
        result.setRefreshExpiration(
                System.currentTimeMillis() + this.globalConfiguration.getSecurityContextRefreshableInterval());
        result.setAuthorId(authorId);
        return result;
    }
}
