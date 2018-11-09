package online.nwen.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Document(collection = "articles")
@CompoundIndexes({
        @CompoundIndex(def = "{'_id': 1, 'systemConfirmedPublish': 1}"),
        @CompoundIndex(def = "{'authorId': 1, 'systemConfirmedPublish': 1}"),
        @CompoundIndex(def = "{'anthologyId': 1, 'systemConfirmedPublish': 1}"),
        @CompoundIndex(def = "{'tags': 1, 'systemConfirmedPublish': 1}")
})
public class Article implements Serializable {
    private static final long serialVersionUID = 8070388670184979679L;
    @Id
    private String id;
    @Indexed
    private String title;
    private Date authorConfirmedPublishDate;
    private Date systemConfirmedPublishDate;
    private Date updateDate;
    private Date createDate;
    private String content;
    private String summary;
    @Indexed
    private String anthologyId;
    @Indexed
    private String authorId;
    private String coverResourceId;
    @Indexed
    private boolean authorConfirmedPublish;
    @Indexed
    private boolean systemConfirmedPublish;
    @Indexed
    private Set<String> tags;
    private Map<String, Date> bookmarks;
    private Map<String, Date> praises;
    private Map<String, Date> viewers;
    private long bookmarksNumber;
    private long praisesNumber;
    private long viewersNumber;
    private long commentNumber;

    public Article() {
        this.createDate = new Date();
        this.tags = new HashSet<>();
        this.bookmarks = new HashMap<>();
        this.praises = new HashMap<>();
        this.viewers = new HashMap<>();
        this.updateDate = this.createDate;
        this.authorConfirmedPublish = false;
        this.systemConfirmedPublish = false;
        this.bookmarksNumber = 0L;
        this.praisesNumber = 0L;
        this.viewersNumber = 0L;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAuthorConfirmedPublishDate() {
        return authorConfirmedPublishDate;
    }

    public void setAuthorConfirmedPublishDate(Date authorConfirmedPublishDate) {
        this.authorConfirmedPublishDate = authorConfirmedPublishDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthorConfirmedPublish(boolean authorConfirmedPublish) {
        this.authorConfirmedPublish = authorConfirmedPublish;
    }

    public boolean isAuthorConfirmedPublish() {
        return authorConfirmedPublish;
    }

    public String getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(String anthologyId) {
        this.anthologyId = anthologyId;
    }

    public String getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(String coverResourceId) {
        this.coverResourceId = coverResourceId;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorConfirmedPublishDate=" + authorConfirmedPublishDate +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                ", anthologyId='" + anthologyId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", coverResourceId='" + coverResourceId + '\'' +
                ", authorConfirmedPublish=" + authorConfirmedPublish +
                ", systemConfirmedPublish=" + systemConfirmedPublish +
                ", tags=" + tags +
                ", bookmarks=" + bookmarks +
                ", praises=" + praises +
                ", viewers=" + viewers +
                ", bookmarksNumber=" + bookmarksNumber +
                ", praisesNumber=" + praisesNumber +
                ", viewersNumber=" + viewersNumber +
                ", commentNumber=" + commentNumber +
                '}';
    }
}
