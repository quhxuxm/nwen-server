package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_security_token")
public class SecurityToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "security_token_id")
    private Long id;
    @Column(name = "security_token", unique = true, nullable = false)
    private String token;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireAt;
    @Column(name = "refreshable_till")
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshableTill;
    @Column(name = "disabled")
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Date getRefreshableTill() {
        return refreshableTill;
    }

    public void setRefreshableTill(Date refreshableTill) {
        this.refreshableTill = refreshableTill;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
