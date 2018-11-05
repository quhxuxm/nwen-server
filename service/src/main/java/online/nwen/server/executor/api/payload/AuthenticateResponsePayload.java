package online.nwen.server.executor.api.payload;

public class AuthenticateResponsePayload {
    private String authorId;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "AuthenticateResponsePayload{" +
                "authorId='" + authorId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
