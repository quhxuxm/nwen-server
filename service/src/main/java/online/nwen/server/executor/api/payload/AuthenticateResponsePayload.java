package online.nwen.server.executor.api.payload;

public class AuthenticateResponsePayload {
    private String authorId;

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
                '}';
    }
}
