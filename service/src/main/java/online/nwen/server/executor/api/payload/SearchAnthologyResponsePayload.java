package online.nwen.server.executor.api.payload;

import org.springframework.data.domain.Page;

public class SearchAnthologyResponsePayload {
    public static class SearchAnthologyRecord {
        private String id;
        private boolean publish;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isPublish() {
            return publish;
        }

        public void setPublish(boolean publish) {
            this.publish = publish;
        }
    }

    private Page<SearchAnthologyRecord> records;

    public SearchAnthologyResponsePayload() {
    }

    public Page<SearchAnthologyRecord> getRecords() {
        return records;
    }

    public void setRecords(
            Page<SearchAnthologyRecord> records) {
        this.records = records;
    }
}
