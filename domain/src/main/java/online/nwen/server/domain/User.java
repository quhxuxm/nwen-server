package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username", length = 48, nullable = false, unique = true)
    private String username;
    @Column(name = "password", length = 24, nullable = false)
    private String password;
    @Column(name = "description", length = 1200)
    private String description;
    @Column(name = "nickname", length = 48, nullable = false, unique = true)
    private String nickname;
    @Column(name = "registered_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp registeredTime;
    @Column(name = "last_login_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastLoginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Timestamp getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Timestamp registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
