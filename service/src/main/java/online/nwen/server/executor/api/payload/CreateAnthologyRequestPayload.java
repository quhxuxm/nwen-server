package online.nwen.server.executor.api.payload;

import java.util.HashSet;
import java.util.Set;

public class CreateAnthologyRequestPayload {
    private String title;
    private String summary;
    private Set<String> tags;
    private boolean publish;

    public CreateAnthologyRequestPayload() {
        this.tags = new HashSet<>();
    }

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

    public Set<String> getTags() {
        return tags;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
