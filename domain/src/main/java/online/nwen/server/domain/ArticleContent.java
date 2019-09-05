package online.nwen.server.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbnwen_article_content")
public class ArticleContent {
    @EmbeddedId
    private ArticleContentId id;
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "content")
    @Lob
    private String content;
    @Column(name = "version_time", nullable = false)
    private Date versionTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArticleContentId getId() {
        return id;
    }

    public void setId(ArticleContentId id) {
        this.id = id;
    }

    public Date getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(Date versionTime) {
        this.versionTime = versionTime;
    }
}
