package online.nwen.server.bo;

import java.util.HashSet;
import java.util.Set;

public class LastReadArticleInBookmarkBo {
    private Long articleId;
    private String articleTitle;
    private String articleDescription;
    private Set<LabelBo> labels;

    public LastReadArticleInBookmarkBo() {
        this.labels = new HashSet<>();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public Set<LabelBo> getLabels() {
        return labels;
    }

    public void setLabels(Set<LabelBo> labels) {
        this.labels = labels;
    }
}
