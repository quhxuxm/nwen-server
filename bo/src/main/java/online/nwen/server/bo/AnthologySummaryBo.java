package online.nwen.server.bo;

import java.util.Date;

public class AnthologySummaryBo {
    private Long anthologyId;
    private String title;
    private String summary;
    private Date createTime;
    private Date updateTime;
    private UserSummaryBo author;

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

    public UserSummaryBo getAuthor() {
        return author;
    }

    public void setAuthor(UserSummaryBo author) {
        this.author = author;
    }

    public Long getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(Long anthologyId) {
        this.anthologyId = anthologyId;
    }
}
