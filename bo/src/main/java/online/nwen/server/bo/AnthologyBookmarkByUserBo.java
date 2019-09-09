package online.nwen.server.bo;

import java.util.Date;

public class AnthologyBookmarkByUserBo {
    private Long anthologyBookmarkId;
    private AnthologySummaryBo anthologySummary;
    private Date createTime;
    private LastReadArticleInBookmarkBo lastReadArticleId;

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

    public LastReadArticleInBookmarkBo getLastReadArticleId() {
        return lastReadArticleId;
    }

    public void setLastReadArticle(LastReadArticleInBookmarkBo lastReadArticleId) {
        this.lastReadArticleId = lastReadArticleId;
    }

    public void setAnthologyBookmarkId(Long anthologyBookmarkId) {
        this.anthologyBookmarkId = anthologyBookmarkId;
    }

    public Long getAnthologyBookmarkId() {
        return anthologyBookmarkId;
    }
}
