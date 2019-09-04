package online.nwen.server.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateArticleRequestBo extends CreateArticleRequestBo {
    @JsonIgnore
    private Long articleId;
    @JsonIgnore
    private Long version;

    public Long getArticleId() {
        return articleId;
    }

    public Long getVersion() {
        return version;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
