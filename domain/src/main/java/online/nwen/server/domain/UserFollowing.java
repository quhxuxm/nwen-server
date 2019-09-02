package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_following", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_id"})
})
public class UserFollowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_following_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", referencedColumnName = "user_id", nullable = false)
    private User target;
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
