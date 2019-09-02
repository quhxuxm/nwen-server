package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tbnwen_anthology_bookmark", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "anthology_id"})
})
public class AnthologyBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "anthology_bookmark_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anthology_id", referencedColumnName = "anthology_id", nullable = false)
    private Article article;
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_read_article_id", referencedColumnName = "article_id")
    private Article lastReadArticle;

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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Article getLastReadArticle() {
        return lastReadArticle;
    }

    public void setLastReadArticle(Article lastReadArticle) {
        this.lastReadArticle = lastReadArticle;
    }
}
