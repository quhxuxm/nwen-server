package online.nwen.server.bo;

import java.util.HashMap;
import java.util.Map;

public class ContentAnalyzeResultBo {
    private String content;
    private Map<String, MediaContent> mediaContents;

    public static class MediaContent {
        private String md5;
        private byte[] content;
        private String contentType;

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
    }

    public ContentAnalyzeResultBo() {
        this.mediaContents = new HashMap<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, MediaContent> getMediaContents() {
        return mediaContents;
    }
}
