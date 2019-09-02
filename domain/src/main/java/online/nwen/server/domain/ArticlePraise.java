package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbnwen_article_praise", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "article_id"})
})
public class ArticlePraise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_praise_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id", nullable = false)
    private Article article;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
