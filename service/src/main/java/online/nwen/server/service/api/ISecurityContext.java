package online.nwen.server.service.api;

public interface ISecurityContext {
    String getAuthorId();

    String getUsername();

    long getRefreshExpiration();
}
