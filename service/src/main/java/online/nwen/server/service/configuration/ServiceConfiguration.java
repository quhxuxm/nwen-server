package online.nwen.server.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
public class ServiceConfiguration {
    private String securitySecret;
    private String securityIssuer;
    private long securityContextRefreshableInterval;
    private long securityContextExpireInterval;

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

    public long getSecurityContextRefreshableInterval() {
        return securityContextRefreshableInterval;
    }

    public void setSecurityContextRefreshableInterval(long securityContextRefreshableInterval) {
        this.securityContextRefreshableInterval = securityContextRefreshableInterval;
    }

    public long getSecurityContextExpireInterval() {
        return securityContextExpireInterval;
    }

    public void setSecurityContextExpireInterval(long securityContextExpireInterval) {
        this.securityContextExpireInterval = securityContextExpireInterval;
    }
}
