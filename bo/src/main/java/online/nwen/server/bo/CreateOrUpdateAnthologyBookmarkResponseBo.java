package online.nwen.server.bo;

import java.util.Date;

public class CreateOrUpdateAnthologyBookmarkResponseBo {
    private Long anthologyBookmarkId;
    private Date createTime;
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAnthologyBookmarkId(Long anthologyBookmarkId) {
        this.anthologyBookmarkId = anthologyBookmarkId;
    }

    public Long getAnthologyBookmarkId() {
        return anthologyBookmarkId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
