package online.nwen.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
public class ServiceConfiguration {
    private String securitySecret;
    private String securityIssuer;
    private long securityContextRefreshInterval;

    public String getSecuritySecret() {
        return securitySecret;
    }

    public void setSecuritySecret(String securitySecret) {
        this.securitySecret = securitySecret;
    }

    public String getSecurityIssuer() {
        return securityIssuer;
    }

    public void setSecurityIssuer(String securityIssuer) {
        this.securityIssuer = securityIssuer;
    }

    public long getSecurityContextRefreshInterval() {
        return securityContextRefreshInterval;
    }

    public void setSecurityContextRefreshInterval(long securityContextRefreshInterval) {
        this.securityContextRefreshInterval = securityContextRefreshInterval;
    }
}
