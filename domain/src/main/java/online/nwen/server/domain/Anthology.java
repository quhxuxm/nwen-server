package online.nwen.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "anthologies")
public class Anthology implements Serializable {
    private static final long serialVersionUID = -190322673132950827L;
    @Id
    private String id;
    private String title;
    private String summary;
    private Date createDate;
    private Date updateDate;
    private Date publishDate;
    private Date sharedDate;
    private String authorId;
    private String coverImageId;
    private Boolean isPublished;
    private Boolean isShared;
    private Long praiseNumber;
    private Long commentsNumber;
    private Long bookmarksNumber;
    private Long articleNumber;
    private Long viewersNumber;
    private Long praisesNumber;
    private Set<String> tags;
    private Set<String> participantAuthorIds;
    private Map<String, Date> bookmarks;
    private Map<String, Date> praises;
    private Map<String, Date> viewers;

    public Anthology() {
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.isPublished = false;
        this.isShared = false;
        this.praiseNumber = 0L;
        this.commentsNumber = 0L;
        this.bookmarksNumber = 0L;
        this.tags = new HashSet<>();
        this.participantAuthorIds = new HashSet<>();
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(String coverImageId) {
        this.coverImageId = coverImageId;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }

    public Long getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(Long praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getParticipantAuthorIds() {
        return participantAuthorIds;
    }

    public void setParticipantAuthorIds(Set<String> participantAuthorIds) {
        this.participantAuthorIds = participantAuthorIds;
    }

    public Long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(Long articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Map<String, Date> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Map<String, Date> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Map<String, Date> getPraises() {
        return praises;
    }

    public void setPraises(Map<String, Date> praises) {
        this.praises = praises;
    }

    public Map<String, Date> getViewers() {
        return viewers;
    }

    public void setViewers(Map<String, Date> viewers) {
        this.viewers = viewers;
    }

    public Long getViewersNumber() {
        return viewersNumber;
    }

    public void setViewersNumber(Long viewersNumber) {
        this.viewersNumber = viewersNumber;
    }

    public Long getPraisesNumber() {
        return praisesNumber;
    }

    public void setPraisesNumber(Long praisesNumber) {
        this.praisesNumber = praisesNumber;
    }

    public Long getBookmarksNumber() {
        return bookmarksNumber;
    }

    public void setBookmarksNumber(Long bookmarksNumber) {
        this.bookmarksNumber = bookmarksNumber;
    }

    public Long getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(Long commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    @Override
    public String toString() {
        return "Anthology{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", publishDate=" + publishDate +
                ", sharedDate=" + sharedDate +
                ", authorId='" + authorId + '\'' +
                ", coverImageId='" + coverImageId + '\'' +
                ", isPublished=" + isPublished +
                ", isShared=" + isShared +
                ", praiseNumber=" + praiseNumber +
                ", commentsNumber=" + commentsNumber +
                ", bookmarksNumber=" + bookmarksNumber +
                ", articleNumber=" + articleNumber +
                ", viewersNumber=" + viewersNumber +
                ", praisesNumber=" + praisesNumber +
                ", tags=" + tags +
                ", participantAuthorIds=" + participantAuthorIds +
                ", bookmarks=" + bookmarks +
                ", praises=" + praises +
                ", viewers=" + viewers +
                '}';
    }
}
