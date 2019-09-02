package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "anthology_comment")
public class AnthologyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "anthology_comment_id")
    private Long id;
    @Column(name = "content")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", referencedColumnName = "user_id", nullable = false)
    private User commenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }
}
