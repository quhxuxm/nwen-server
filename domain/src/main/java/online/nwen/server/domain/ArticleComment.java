package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_article_comment")
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_comment_id")
    private Long id;
    @Column(name = "content")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", referencedColumnName = "user_id", nullable = false)
    private User commenter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id", nullable = false)
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_comment_id", referencedColumnName = "article_comment_id")
    private ArticleComment replyTo;
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

    public ArticleComment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ArticleComment replyTo) {
        this.replyTo = replyTo;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
