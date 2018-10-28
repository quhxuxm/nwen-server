package online.nwen.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
public class ServiceConfiguration {
    private String securitySecret;

    public String getSecuritySecret() {
        return securitySecret;
    }

    public void setSecuritySecret(String securitySecret) {
        this.securitySecret = securitySecret;
    }
}
