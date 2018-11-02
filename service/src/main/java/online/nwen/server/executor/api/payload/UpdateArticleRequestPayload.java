package online.nwen.server.executor.api.payload;

import java.util.HashSet;
import java.util.Set;

public class UpdateArticleRequestPayload {
    private String articleId;
    private String title;
    private String summary;
    private String content;
    private Set<String> tags;

    public UpdateArticleRequestPayload() {
        this.tags = new HashSet<>();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getTags() {
        return tags;
    }
}
