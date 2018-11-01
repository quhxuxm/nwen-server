package online.nwen.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "expired_secure_token")
public class ExpiredSecureToken implements Serializable {
    private static final long serialVersionUID = -190322673132950117L;
    @Id
    private String id;
    @Indexed(expireAfterSeconds = 3600, unique = true)
    private String token;
    private Date markExpiredAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getMarkExpiredAt() {
        return markExpiredAt;
    }

    public void setMarkExpiredAt(Date markExpiredAt) {
        this.markExpiredAt = markExpiredAt;
    }
}
