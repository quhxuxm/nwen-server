package online.nwen.server.payload;

public class AuthenticateResponsePayload {
    private String secureToken;

    public String getSecureToken() {
        return secureToken;
    }

    public void setSecureToken(String secureToken) {
        this.secureToken = secureToken;
    }
}
