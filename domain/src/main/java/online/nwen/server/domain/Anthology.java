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
    private Date authorConfirmedPublishDate;
    private Date systemConfirmedPublishDate;
    private Date sharedDate;
    private String authorId;
    private String coverImageId;
    private boolean systemConfirmedPublish;
    private boolean authorConfirmedPublish;
    private boolean share;
    private long praiseNumber;
    private long commentsNumber;
    private long bookmarksNumber;
    private long articleNumber;
    private long viewersNumber;
    private long praisesNumber;
    private Set<String> tags;
    private Set<String> participantAuthorIds;
    private Map<String, Date> bookmarks;
    private Map<String, Date> praises;
    private Map<String, Date> viewers;

    public Anthology() {
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.authorConfirmedPublish = false;
        this.systemConfirmedPublish = false;
        this.share = false;
        this.praiseNumber = 0L;
        this.commentsNumber = 0L;
        this.bookmarksNumber = 0L;
        this.tags = new HashSet<>();
        this.participantAuthorIds = new HashSet<>();
    }

    public boolean isSystemConfirmedPublish() {
        return systemConfirmedPublish;
    }

    public void setSystemConfirmedPublish(boolean systemConfirmedPublish) {
        this.systemConfirmedPublish = systemConfirmedPublish;
    }

    public Date getSystemConfirmedPublishDate() {
        return systemConfirmedPublishDate;
    }

    public void setSystemConfirmedPublishDate(Date systemConfirmedPublishDate) {
        this.systemConfirmedPublishDate = systemConfirmedPublishDate;
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

    public Date getAuthorConfirmedPublishDate() {
        return authorConfirmedPublishDate;
    }

    public void setAuthorConfirmedPublishDate(Date publishDate) {
        this.authorConfirmedPublishDate = publishDate;
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

    public boolean isAuthorConfirmedPublish() {
        return authorConfirmedPublish;
    }

    public void setAuthorConfirmedPublish(boolean authorConfirmedPublish) {
        this.authorConfirmedPublish = authorConfirmedPublish;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isShare() {
        return share;
    }

    public long getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(long praiseNumber) {
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

    public long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(long articleNumber) {
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

    public long getViewersNumber() {
        return viewersNumber;
    }

    public void setViewersNumber(long viewersNumber) {
        this.viewersNumber = viewersNumber;
    }

    public long getPraisesNumber() {
        return praisesNumber;
    }

    public void setPraisesNumber(long praisesNumber) {
        this.praisesNumber = praisesNumber;
    }

    public long getBookmarksNumber() {
        return bookmarksNumber;
    }

    public void setBookmarksNumber(long bookmarksNumber) {
        this.bookmarksNumber = bookmarksNumber;
    }

    public long getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(long commentsNumber) {
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
                ", authorConfirmedPublishDate=" + authorConfirmedPublishDate +
                ", sharedDate=" + sharedDate +
                ", authorId='" + authorId + '\'' +
                ", coverImageId='" + coverImageId + '\'' +
                ", systemConfirmedPublish=" + systemConfirmedPublish +
                ", authorConfirmedPublish=" + authorConfirmedPublish +
                ", share=" + share +
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
