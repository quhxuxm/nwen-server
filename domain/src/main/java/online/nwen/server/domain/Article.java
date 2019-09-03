package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_article", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"anthology_id", "index_in_anthology"})
})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long id;
    @Column(name = "title", length = 200, nullable = false)
    private String title;
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    @Lob
    private String content;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "summary", length = 800)
    private String summary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anthology_id", referencedColumnName = "anthology_id", nullable = false)
    private Anthology anthology;
    @Version
    @Column(name = "version")
    private Long version;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Anthology getAnthology() {
        return anthology;
    }

    public void setAnthology(Anthology anthology) {
        this.anthology = anthology;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
