package online.nwen.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "resources")
public class Resource implements Serializable {
    private static final long serialVersionUID = -7001454343688484844L;
    @Id
    private String id;
    private byte[] content;
    @Indexed
    private String contentType;
    @Indexed(unique = true)
    private String md5;
    @Indexed
    private String saveAuthorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSaveAuthorId() {
        return saveAuthorId;
    }

    public void setSaveAuthorId(String saveAuthorId) {
        this.saveAuthorId = saveAuthorId;
    }
}
