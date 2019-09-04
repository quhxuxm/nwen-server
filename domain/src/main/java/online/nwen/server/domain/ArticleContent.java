package online.nwen.server.domain;

import javax.persistence.*;

@Entity
@Table(name = "tbnwen_article_content")
public class ArticleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_content_id")
    private Long id;
    @Column(name = "content")
    @Lob
    private String content;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
