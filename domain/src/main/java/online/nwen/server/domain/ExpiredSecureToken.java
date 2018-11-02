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
    @Indexed(unique = true)
    private String token;
    @Indexed(expireAfterSeconds = 1)
    private Date recodeExpireAt;

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

    public Date getRecodeExpireAt() {
        return recodeExpireAt;
    }

    public void setRecodeExpireAt(Date recodeExpireAt) {
        this.recodeExpireAt = recodeExpireAt;
    }
}
