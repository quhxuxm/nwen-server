package online.nwen.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Document(collection = "authors")
public class Author implements Serializable {
    private static final long serialVersionUID = -2652995801468036436L;
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String iconImageId;
    private String description;
    @Indexed(unique = true)
    private String nickname;
    private Set<Role> roles;
    private Date registerDate;
    private Date lastLoginDate;
    private Set<String> tags;
    private Map<String, Date> followers;
    private Map<String, Date> following;
    private String defaultAnthologyId;
    private Long anthologyNumber;
    private Long articleNumber;
    private Long commentNumber;
    private Long followerNumber;

    public Author() {
        this.registerDate = new Date();
        this.tags = new HashSet<>();
        this.roles = new HashSet<>();
        this.followers = new HashMap<>();
        this.following = new HashMap<>();
        this.anthologyNumber = 0L;
        this.articleNumber = 0L;
        this.commentNumber = 0L;
        this.followerNumber = 0L;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIconImageId() {
        return iconImageId;
    }

    public void setIconImageId(String iconImageId) {
        this.iconImageId = iconImageId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Map<String, Date> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, Date> followers) {
        this.followers = followers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getDefaultAnthologyId() {
        return defaultAnthologyId;
    }

    public void setDefaultAnthologyId(String defaultAnthologyId) {
        this.defaultAnthologyId = defaultAnthologyId;
    }

    public Long getAnthologyNumber() {
        return anthologyNumber;
    }

    public void setAnthologyNumber(Long anthologyNumber) {
        this.anthologyNumber = anthologyNumber;
    }

    public Long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(Long articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Long getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Long commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Long getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(Long followerNumber) {
        this.followerNumber = followerNumber;
    }

    public Map<String, Date> getFollowing() {
        return following;
    }

    public void setFollowing(Map<String, Date> following) {
        this.following = following;
    }
}
