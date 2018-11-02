package online.nwen.server.executor.api.payload;

import java.util.HashSet;
import java.util.Set;

public class UpdateAnthologyRequestPayload {
    private String anthologyId;
    private String title;
    private String summary;
    private Set<String> tags;

    public UpdateAnthologyRequestPayload() {
        this.tags = new HashSet<>();
    }

    public String getAnthologyId() {
        return anthologyId;
    }

    public void setAnthologyId(String anthologyId) {
        this.anthologyId = anthologyId;
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
}