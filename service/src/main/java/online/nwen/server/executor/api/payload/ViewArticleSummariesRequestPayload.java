package online.nwen.server.executor.api.payload;

import java.util.HashSet;
import java.util.Set;

public class ViewArticleSummariesRequestPayload {
    private Set<String> ids;

    public ViewArticleSummariesRequestPayload() {
        this.ids = new HashSet<>();
    }

    public Set<String> getIds() {
        return ids;
    }
}
