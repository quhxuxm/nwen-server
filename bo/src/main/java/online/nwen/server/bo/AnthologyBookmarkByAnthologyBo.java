package online.nwen.server.bo;

import java.util.Date;

public class AnthologyBookmarkByAnthologyBo {
    private Long anthologyBookmarkId;
    private UserSummaryBo authorSummary;
    private Date createTime;
    private LastReadArticleInBookmarkBo lastReadArticleId;

    public UserSummaryBo getAuthorSummary() {
        return authorSummary;
    }

    public void setAuthorSummary(UserSummaryBo authorSummary) {
        this.authorSummary = authorSummary;
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
