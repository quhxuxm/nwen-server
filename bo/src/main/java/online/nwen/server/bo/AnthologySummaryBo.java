package online.nwen.server.bo;

import java.util.Date;

public class AnthologySummaryBo {
    private Long anthologyId;
    private String title;
    private String description;
    private Date createTime;
    private Date updateTime;
    private UserSummaryBo author;

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