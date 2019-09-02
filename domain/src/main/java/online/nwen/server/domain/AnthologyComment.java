package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_anthology_comment")
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
    private Date createTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", referencedColumnName = "user_id", nullable = false)
    private User commenter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anthology_id", referencedColumnName = "anthology_id", nullable = false)
    private Anthology anthology;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_comment_id", referencedColumnName = "anthology_comment_id")
    private AnthologyComment replyTo;
    @Version
    @Column(name = "version")
    private Long version;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public AnthologyComment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(AnthologyComment replyTo) {
        this.replyTo = replyTo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Anthology getAnthology() {
        return anthology;
    }

    public void setAnthology(Anthology anthology) {
        this.anthology = anthology;
    }
}
