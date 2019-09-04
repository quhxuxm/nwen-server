package online.nwen.server.bo;

import java.util.Date;

public class ArticleSummaryBo {
    private Long articleId;
    private String title;
    private String description;
    private Date createTime;
    private Date updateTime;
    private AnthologySummaryBo anthologySummary;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public AnthologySummaryBo getAnthologySummary() {
        return anthologySummary;
    }

    public void setAnthologySummary(AnthologySummaryBo anthologySummary) {
        this.anthologySummary = anthologySummary;
    }
}
