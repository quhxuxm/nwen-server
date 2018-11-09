package online.nwen.server.service.impl;

import online.nwen.server.service.api.ISecurityContext;

class SecurityContext implements ISecurityContext {
    private long refreshExpiration;
    private String authorId;

    void setRefreshExpiration(long refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
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
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
