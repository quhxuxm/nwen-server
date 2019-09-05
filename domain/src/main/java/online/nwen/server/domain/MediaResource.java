package online.nwen.server.domain;

import javax.persistence.*;

@Entity
@Table(name = "tbnwen_media_resource")
public class MediaResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "md5", unique = true, length = 64)
    private String md5;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "content")
    @Lob
    private byte[] content;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
