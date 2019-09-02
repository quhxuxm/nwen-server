package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbnwen_anthology_bookmark", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "anthology_id"})
})
public class AnthologyFollowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "anthology_following_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anthology_id", referencedColumnName = "anthology_id", nullable = false)
    private Anthology anthology;
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

    public Anthology getAnthology() {
        return anthology;
    }

    public void setAnthology(Anthology anthology) {
        this.anthology = anthology;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
