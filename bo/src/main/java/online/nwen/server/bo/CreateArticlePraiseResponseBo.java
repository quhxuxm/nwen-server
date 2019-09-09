package online.nwen.server.bo;

import java.util.Date;

public class CreateArticlePraiseResponseBo {
    private Long articlePraiseId;
    private Long articleId;
    private Date createTime;

    public Long getArticlePraiseId() {
        return articlePraiseId;
    }

    public void setArticlePraiseId(Long articlePraiseId) {
        this.articlePraiseId = articlePraiseId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
