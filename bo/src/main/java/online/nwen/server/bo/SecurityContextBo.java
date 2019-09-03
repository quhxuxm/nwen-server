package online.nwen.server.bo;

import java.util.Date;

public class SecurityContextBo {
    private String username;
    private Date refreshAbleTill;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRefreshAbleTill() {
        return refreshAbleTill;
    }

    public void setRefreshAbleTill(Date refreshAbleTill) {
        this.refreshAbleTill = refreshAbleTill;
    }
}
