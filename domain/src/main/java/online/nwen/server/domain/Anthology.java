package online.nwen.server.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbnwen_anthology")
public class Anthology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "anthology_id")
    private Long id;
    @Column(name = "title", length = 200, nullable = false)
    private String title;
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateTime;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "summary", length = 800)
    private String summary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", nullable = false)
    private User author;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_article", referencedColumnName = "article_id", unique = true)
    private Article lastArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getLastArticle() {
        return lastArticle;
    }

    public void setLastArticle(Article lastArticle) {
        this.lastArticle = lastArticle;
    }
}
