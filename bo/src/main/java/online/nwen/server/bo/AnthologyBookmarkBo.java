package online.nwen.server.bo;

import java.util.Date;

public class AnthologyBookmarkBo {
    private UserSummaryBo authorSummary;
    private AnthologySummaryBo anthologySummary;
    private Date createTime;
    private ArticleSummaryBo lastReadArticle;

    public UserSummaryBo getAuthorSummary() {
        return authorSummary;
    }

    public void setAuthorSummary(UserSummaryBo authorSummary) {
        this.authorSummary = authorSummary;
    }

    public AnthologySummaryBo getAnthologySummary() {
        return anthologySummary;
    }

    public void setAnthologySummary(AnthologySummaryBo anthologySummary) {
        this.anthologySummary = anthologySummary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ArticleSummaryBo getLastReadArticle() {
        return lastReadArticle;
    }

    public void setLastReadArticle(ArticleSummaryBo lastReadArticle) {
        this.lastReadArticle = lastReadArticle;
    }
}
