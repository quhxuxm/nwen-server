package online.nwen.server.executor.api.payload;

import java.util.HashSet;
import java.util.Set;

public class ViewAuthorDetailResponsePayload {
    private String username;
    private String nickname;
    private Set<String> tags;
    private String description;
    private String id;
    private long anthologyNumber;
    private long articleNumber;
    private long commentNumber;
    private long followerNumber;

    public ViewAuthorDetailResponsePayload() {
        this.tags = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAnthologyNumber() {
        return anthologyNumber;
    }

    public void setAnthologyNumber(long anthologyNumber) {
        this.anthologyNumber = anthologyNumber;
    }

    public long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(long articleNumber) {
        this.articleNumber = articleNumber;
    }

    public long getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(long commentNumber) {
        this.commentNumber = commentNumber;
    }

    public long getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(long followerNumber) {
        this.followerNumber = followerNumber;
    }
}
