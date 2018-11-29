package online.nwen.server.executor.api.payload;

import java.util.Set;

public class AuthenticateResponsePayload {
    private String authorId;
    private String authorNickname;
    private String authorUsername;
    private String authorDescription;
    private Set<String> authorTags;
    private long authorLastLoginDate;
    private String authorIconImageId;
    private String authorDefaultAnthologyId;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public Set<String> getAuthorTags() {
        return authorTags;
    }

    public void setAuthorTags(Set<String> authorTags) {
        this.authorTags = authorTags;
    }

    public long getAuthorLastLoginDate() {
        return authorLastLoginDate;
    }

    public void setAuthorLastLoginDate(long authorLastLoginDate) {
        this.authorLastLoginDate = authorLastLoginDate;
    }

    public String getAuthorIconImageId() {
        return authorIconImageId;
    }

    public void setAuthorIconImageId(String authorIconImageId) {
        this.authorIconImageId = authorIconImageId;
    }

    public String getAuthorDefaultAnthologyId() {
        return authorDefaultAnthologyId;
    }

    public void setAuthorDefaultAnthologyId(String authorDefaultAnthologyId) {
        this.authorDefaultAnthologyId = authorDefaultAnthologyId;
    }

    @Override
    public String toString() {
        return "AuthenticateResponsePayload{" +
                "authorId='" + authorId + '\'' +
                ", authorNickname='" + authorNickname + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                ", authorDescription='" + authorDescription + '\'' +
                ", authorTags=" + authorTags +
                ", authorLastLoginDate=" + authorLastLoginDate +
                ", authorIconImageId='" + authorIconImageId + '\'' +
                ", authorDefaultAnthologyId='" + authorDefaultAnthologyId + '\'' +
                '}';
    }
}
