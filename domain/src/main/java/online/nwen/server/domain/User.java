package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_user")
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
    private Date registeredTime;
    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_anthology_id", referencedColumnName = "anthology_id")
    private Anthology defaultAnthology;

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

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Anthology getDefaultAnthology() {
        return defaultAnthology;
    }

    public void setDefaultAnthology(Anthology defaultAnthology) {
        this.defaultAnthology = defaultAnthology;
    }
}
