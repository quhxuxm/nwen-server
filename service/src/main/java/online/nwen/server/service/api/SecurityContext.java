package online.nwen.server.service.api;

public class SecurityContext {
    private final long refreshExpiration;

    public SecurityContext(long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }
}
