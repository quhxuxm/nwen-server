package online.nwen.server.service.api;

public interface ISecurityContext {
    String getUsername();

    long getRefreshExpiration();
}
