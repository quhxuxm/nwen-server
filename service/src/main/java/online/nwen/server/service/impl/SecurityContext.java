package online.nwen.server.service.impl;

import online.nwen.server.service.api.ISecurityContext;

class SecurityContext implements ISecurityContext {
    private long refreshExpiration;
    private String username;
    private String authorId;

    void setRefreshExpiration(long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "SecurityContext{" +
                "refreshExpiration=" + refreshExpiration +
                ", username='" + username + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
