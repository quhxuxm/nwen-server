package online.nwen.server.executor.api.payload;

import java.util.Date;
import java.util.Set;

public class ViewAnthologySummaryResponsePayload {
    private String id;
    private String title;
    private String summary;
    private String authorNickname;
    private String authorId;
    private String authorIconImageId;
    private long bookmarksNumber;
    private long praisesNumber;
    private long viewersNumber;
    private long commentNumber;
    private String coverImageId;
    private Set<String> tags;
    private Date updateDate;
    private Date createDate;
    private Date publishDate;
    private long articleNumber;
    private boolean publish;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorIconImageId() {
        return authorIconImageId;
    }

    public void setAuthorIconImageId(String authorIconImageId) {
        this.authorIconImageId = authorIconImageId;
    }

    public long getBookmarksNumber() {
        return bookmarksNumber;
    }

    public void setBookmarksNumber(long bookmarksNumber) {
        this.bookmarksNumber = bookmarksNumber;
    }

    public long getPraisesNumber() {
        return praisesNumber;
    }

    public void setPraisesNumber(long praisesNumber) {
        this.praisesNumber = praisesNumber;
    }

    public long getViewersNumber() {
        return viewersNumber;
    }

    public void setViewersNumber(long viewersNumber) {
        this.viewersNumber = viewersNumber;
    }

    public long getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(long commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(long articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(String coverImageId) {
        this.coverImageId = coverImageId;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public boolean isPublish() {
        return publish;
    }
}