package online.nwen.server.executor.api.payload;

public class AuthenticateResponsePayload {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthenticateResponsePayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
