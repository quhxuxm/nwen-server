package online.nwen.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.nwen.server.configuration.ServiceConfiguration;
import online.nwen.server.service.api.ISecurityContext;
import online.nwen.server.service.api.ISecurityService;
import org.springframework.stereotype.Service;

@Service
class SecurityService implements ISecurityService {
    private Algorithm algorithm;
    private ObjectMapper objectMapper;

    public SecurityService(ServiceConfiguration serviceConfiguration) {
        this.algorithm = Algorithm.HMAC256(serviceConfiguration.getSecuritySecret());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String generateSecureToken(ISecurityContext securityContext) {
        try {
            return JWT.create().withSubject(this.objectMapper.writeValueAsString(securityContext)).sign(this.algorithm);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ISecurityContext parseSecurityContext(String secureToken) {
        return null;
    }
}
