package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_bl_security_token")
public class BlackListSecurityToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bl_security_token_id")
    private Long id;
    @Column(name = "bl_security_token", unique = true, nullable = false)
    private String token;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

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
}
