package online.nwen.server.executor.api.payload;

import org.springframework.data.domain.Page;

public class SearchAnthologyResponsePayload {
    public static class SearchAnthologyRecord {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
