package online.nwen.server.bo;

import java.util.Date;

public class AnthologyBookmarkBo {
    private Long anthologyBookmarkId;
    private UserSummaryBo authorSummary;
    private AnthologySummaryBo anthologySummary;
    private Date createTime;
    private Long lastReadArticleId;

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

    public Long getLastReadArticleId() {
        return lastReadArticleId;
    }

    public void setLastReadArticleId(Long lastReadArticleId) {
        this.lastReadArticleId = lastReadArticleId;
    }

    public void setAnthologyBookmarkId(Long anthologyBookmarkId) {
        this.anthologyBookmarkId = anthologyBookmarkId;
    }

    public Long getAnthologyBookmarkId() {
        return anthologyBookmarkId;
    }
}
