package online.nwen.server.service.impl;

import online.nwen.server.service.api.ISecurityContext;

class SecurityContext implements ISecurityContext {
    private long refreshExpiration;
    private String username;

    public void setRefreshExpiration(long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
