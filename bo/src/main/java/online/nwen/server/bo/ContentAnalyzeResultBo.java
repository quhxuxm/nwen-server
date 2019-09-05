package online.nwen.server.bo;

import java.util.HashMap;
import java.util.Map;

public class ContentAnalyzeResultBo {
    private String content;
    private Map<String, MediaResourceBo> mediaContents;

    public ContentAnalyzeResultBo() {
        this.mediaContents = new HashMap<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, MediaResourceBo> getMediaContents() {
        return mediaContents;
    }
}
